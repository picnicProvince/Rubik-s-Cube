package ru.nsu.gorlova;

import java.util.ArrayList;
import java.util.List;

public final class Analyzer {
    public Report analyze(String scramble, String assembly) {
        CubeState cube = new CubeState();
        for (Move m : MoveParser.parse(scramble)) {
            cube.rotate(m);
        }
        ArrayList<Move> moves = MoveParser.parse(assembly);
        int totalMoves = moves.size();
        if(cube.isFLLSolved()) {
            return null;
        }
        Report.StageInfo empty = new Report.StageInfo(0, 0, 0, false);
        Report.StageInfo cross = empty, fll = empty, oll = empty, pll = empty;
        List<Report.AnalysisError> errors = new ArrayList<>();

        CFOP cfop = new CFOP(cube, moves);
        int currentIndex = 0;

        while (currentIndex < moves.size()) {
            if (cube.isPLLSolved()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = currentIndex; i < totalMoves; i++) {
                    stringBuilder.append(moves.get(i).toString());
                    stringBuilder.append(" ");
                }
                errors.add(new Report.AnalysisError(Report.StageName.PLL,
                        currentIndex, moves.size() - 1, stringBuilder.toString(),
                        "The cube was solved before the notation ended..%n"));
                pll = new Report.StageInfo(oll.getEndMoves() + 1,
                        currentIndex - 1, currentIndex - oll.getEndMoves() - 1, true);
                break;
            }
            else if (cube.isOLLSolved()) {
                if (!oll.exists()) {
                    oll = new Report.StageInfo(fll.endMove() + 1, currentIndex - 1, currentIndex - fll.endMove() - 1, true);
                }
                Report.AnalysisError report = cfop.pLL(currentIndex);
                currentIndex = report.getMoveEnd() + 1;
                if (report.suggestion() != null) {
                    errors.add(report);
                }
            }
            else if (cube.isFLLSolved()) {
                if (!fll.exists()) {
                    fll = new Report.StageInfo(cross.endMove() + 1, currentIndex - 1, currentIndex - cross.endMove() - 1, true);
                }
                Report.AnalysisError report = cfop.oLL(currentIndex);
                currentIndex = report.getMoveEnd() + 1;
                if (report.suggestion() != null) {
                    errors.add(report);
                }
            }
            else if (cube.isCrossSolved()) {
                if (!cross.exists()) {
                    cross = new Report.StageInfo(0, currentIndex - 1, currentIndex, true);
                }
                Report.AnalysisError report = cfop.fLL(currentIndex, null);
                currentIndex = report.getMoveEnd() + 1;
                if (report.suggestion() != null) {
                    errors.add(report);
                }
            }
            else {
                cube.rotate(moves.get(currentIndex));
                currentIndex++;
            }
        }
        boolean solved = cube.isFLLSolved();
        if (!pll.exists() && solved) {
            pll = new Report.StageInfo(oll.endMove() + 1, moves.size(), currentIndex - oll.endMove() - 1, true);
        } else if (!cross.exists()) {
            cross = new Report.StageInfo(0, currentIndex - 1, moves.size() - 1, false);
        } else if (!fll.exists()) {
            fll = new Report.StageInfo(cross.endMove() + 1, currentIndex - 1, moves.size() - cross.endMove(), false);
        } else if (!oll.exists()) {
            oll = new Report.StageInfo(fll.endMove() + 1, currentIndex - 1, moves.size() - cross.endMove() -pll.endMove(), false);
        } else if (!pll.exists()) {
            pll = new Report.StageInfo(oll.endMove() + 1, currentIndex - 1, moves.size() - cross.endMove() - pll.endMove() - oll.endMove(), false);
        }
        Report.Stages stages = new Report.Stages(cross, fll, oll, pll);
        return new Report(solved, totalMoves, stages, errors);
    }
}
