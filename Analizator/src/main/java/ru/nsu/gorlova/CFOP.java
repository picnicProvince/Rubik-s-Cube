package ru.nsu.gorlova;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CFOP {
    /**
     * Поворот относительно оси белый-желтый. F -> R -> B -> L
     */
    private int rotationWY;

    /**
     * Поворот относительно оси зелено-синий. Относительно (U) U -> L -> D -> R
     */
    private int rotationGB;

    /**
     * Поворот относительно оси красно-желтый. Относительно (F) F -> U -> B -> D
     */
    private int rotationRO;

    private final CubeState cube;

    private final ArrayList<Move> moves;

    /**
     * Индексы белой и желтой грани при повороте.
     */
    private final int[] up_down = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            6, 3, 0, 7, 4, 1, 8, 5, 2,
            11, 14, 17, 10, 13, 16, 9, 12, 15,
            8, 7, 6, 5, 4, 3, 2, 1, 0,
            17, 16, 15, 14, 13, 12, 11, 10, 9,
            2, 5, 8, 1, 4, 7, 0, 3, 6,
            15, 12, 9, 16, 13, 10, 17, 14, 11};

    private int start;

    private Move lastPLL;

    public CFOP(CubeState cube, ArrayList<Move> moves) {
        this.rotationWY = 0;
        this.rotationGB = 0;
        this.rotationRO = 0;
        this.cube = cube;
        this.moves = moves;
        this.start = 0;
        this.lastPLL = null;
    }

    private Move rotateWY(Move move) {
        Face face = move.face();
        if (face == Face.U || face == Face.D || rotationWY == 0) {
            return move;
        }
        switch (rotationWY) {
            case 1 -> {
                switch (face) {
                    case F -> {
                        return new Move(Face.R, move.turn());
                    }
                    case R -> {
                        return new Move(Face.B, move.turn());
                    }
                    case B -> {
                        return new Move(Face.L, move.turn());
                    }
                    case L -> {
                        return new Move(Face.F, move.turn());
                    }
                }
            }
            case 2 -> {
                switch (face) {
                    case F -> {
                        return new Move(Face.B, move.turn());
                    }
                    case R -> {
                        return new Move(Face.L, move.turn());
                    }
                    case B -> {
                        return new Move(Face.F, move.turn());
                    }
                    case L -> {
                        return new Move(Face.R, move.turn());
                    }
                }
            }
            case 3 -> {
                switch (face) {
                    case F -> {
                        return new Move(Face.L, move.turn());
                    }
                    case R -> {
                        return new Move(Face.F, move.turn());
                    }
                    case B -> {
                        return new Move(Face.R, move.turn());
                    }
                    case L -> {
                        return new Move(Face.B, move.turn());
                    }
                }
            }
        }
        return null;
    }

    private Move rotateGB(Move move) {
        Face face = move.face();
        if (face == Face.F || face == Face.B || rotationGB == 0) {
            return move;
        }
        switch (rotationGB) {
            case 1 -> {
                switch (face) {
                    case U -> {
                        return new Move(Face.L, move.turn());
                    }
                    case R -> {
                        return new Move(Face.U, move.turn());
                    }
                    case D -> {
                        return new Move(Face.R, move.turn());
                    }
                    case L -> {
                        return new Move(Face.D, move.turn());
                    }
                }
            }
            case 2 -> {
                switch (face) {
                    case U -> {
                        return new Move(Face.B, move.turn());
                    }
                    case R -> {
                        return new Move(Face.L, move.turn());
                    }
                    case D -> {
                        return new Move(Face.F, move.turn());
                    }
                    case L -> {
                        return new Move(Face.R, move.turn());
                    }
                }
            }
            case 3 -> {
                switch (face) {
                    case U -> {
                        return new Move(Face.R, move.turn());
                    }
                    case R -> {
                        return new Move(Face.B, move.turn());
                    }
                    case D -> {
                        return new Move(Face.L, move.turn());
                    }
                    case L -> {
                        return new Move(Face.U, move.turn());
                    }
                }
            }
        }
        return null;
    }

    private Move rotateRO(Move move) {
        Face face = move.face();
        if (face == Face.L || face == Face.R || rotationRO == 0) {
            return move;
        }
        switch (rotationRO) {
            case 1 -> {
                switch (face) {
                    case U -> {
                        return new Move(Face.B, move.turn());
                    }
                    case F -> {
                        return new Move(Face.U, move.turn());
                    }
                    case D -> {
                        return new Move(Face.F, move.turn());
                    }
                    case B -> {
                        return new Move(Face.D, move.turn());
                    }
                }
            }
            case 2 -> {
                switch (face) {
                    case U -> {
                        return new Move(Face.D, move.turn());
                    }
                    case F -> {
                        return new Move(Face.B, move.turn());
                    }
                    case D -> {
                        return new Move(Face.U, move.turn());
                    }
                    case B -> {
                        return new Move(Face.F, move.turn());
                    }
                }
            }
            case 3 -> {
                switch (face) {
                    case U -> {
                        return new Move(Face.F, move.turn());
                    }
                    case F -> {
                        return new Move(Face.D, move.turn());
                    }
                    case D -> {
                        return new Move(Face.B, move.turn());
                    }
                    case B -> {
                        return new Move(Face.U, move.turn());
                    }
                }
            }
        }
        return null;
    }

    private int index(int number) {
        if (rotationWY == 0) {
            return number;
        }
        if (0 <= number && number <= 17) {
            return up_down[18 * rotationWY + number];
        }
        if (rotationWY == 1) {
            if (18 <= number && number <= 26) {
                return number + 18;
            }
            if (36 <= number && number <= 44) {
                return number - 9;
            }
            if (27 <= number && number <= 35) {
                return number + 18;
            }
            if (45 <= number && number <= 53) {
                return number - 27;
            }
        }
        if (rotationWY == 2) {
            if (18 <= number && number <= 26) {
                return number + 9;
            }
            if (36 <= number && number <= 44) {
                return number + 9;
            }
            if (27 <= number && number <= 35) {
                return number - 9;
            }
            if (45 <= number && number <= 53) {
                return number - 9;
            }
        }
        if (rotationWY == 3) {
            if (18 <= number && number <= 26) {
                return number + 27;
            }
            if (36 <= number && number <= 44) {
                return number - 18;
            }
            if (27 <= number && number <= 35) {
                return number + 9;
            }
            if (45 <= number && number <= 53) {
                return number - 18;
            }
        }
        return -1;
    }

    private boolean chek(ArrayList<Move> rules) {
        if (rules.size() > moves.size() - start) {
            return false;
        }
        for (int j = 0; j < rules.size(); j++) {
            if (!Objects.equals(rotateWY(rules.get(j)), moves.get(start + j))) {
                return false;
            }
        }
        return true;
    }

    private boolean chekStartWY(ArrayList<Move> rules, int startIndex, int rotate) {
        if (rules.size() > moves.size()) {
            return false;
        }
        int oldRotationWY = rotationWY;
        rotationWY = rotate;
        for (int j = 0; j < rules.size(); j++) {
            if (!Objects.equals(rotateWY(rules.get(j)), moves.get(startIndex + j))) {
                rotationWY = oldRotationWY;
                return false;
            }
        }
        rotationWY = oldRotationWY;
        return true;
    }

    private boolean chekStartRO(ArrayList<Move> rules, int startIndex, int rotate) {
        if (rules.size() > moves.size()) {
            return false;
        }
        int oldRotationRO = rotationRO;
        rotationRO = rotate;
        for (int j = 0; j < rules.size(); j++) {
            if (!Objects.equals(rotateRO(Objects.requireNonNull(rotateWY(rules.get(j)))), moves.get(startIndex + j))) {
                rotationRO = oldRotationRO;
                return false;
            }
        }
        rotationRO = oldRotationRO;
        return true;
    }

    private boolean chekStartGB(ArrayList<Move> rules, int startIndex, int rotate) {
        if (rules.size() > moves.size()) {
            return false;
        }
        int oldRotationGB = rotationGB;
        rotationGB = rotate;
        for (int j = 0; j < rules.size(); j++) {
            if (!Objects.equals(rotateGB(Objects.requireNonNull(rotateWY(rules.get(j)))), moves.get(startIndex + j))) {
                rotationGB = oldRotationGB;
                return false;
            }
        }
        rotationGB = oldRotationGB;
        return true;
    }

    private boolean situationFLL(int red1, int red2, int blue1, int blue2, int white) {
        int blue = index(40) / 9;
        int red = index(31) / 9;
        return cube.getSticker(index(red1)) == red && cube.getSticker(index(red2)) == red
                && cube.getSticker(index(blue1)) == blue && cube.getSticker(index(blue2)) == blue
                && cube.getSticker(index(white)) == 1;
    }

    private Report.AnalysisError goodFLLReport (int ruleSize) {
        for (Move m : moves.subList(start, start + ruleSize)) {
            cube.rotate(m);
        }
        return new Report.AnalysisError(Report.StageName.F2L, start, start + ruleSize - 1,
                null, null);
    }
// up вспомогательный аргумент при применении использовать null
    public Report.AnalysisError fLL(int startIndex, Move up) {
        this.start = startIndex;
        String algorithmNameSuggestion = "No";
        String algorithmName;
        int minLengths = 100;
        for (int i = 0; i < 4; i++){
            rotationWY = i;

            // 1. Basic cases
            if (situationFLL(8, 46, 38, 1, 27)) {
                algorithmName = "R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(27, 3, 8, 19, 38)) {
                algorithmName = "F' U' F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(27, 28, 8, 5, 38)) {
                algorithmName = "U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(7, 8, 37, 38, 27)) {
                algorithmName = "U' F' U F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            // 2. Corner and edge in top
            if (situationFLL(8, 28, 5, 38, 27)) {
                algorithmName = "U' R U' R' U R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(7, 27, 8, 37, 38)) {
                algorithmName = "U F' U F U' F' U' F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(8, 19, 3, 38, 27)) {
                algorithmName = "U' R U R' U R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(1, 27, 8, 46, 38)) {
                algorithmName = "U F' U' F U' F' U' F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(8, 37, 7, 38, 27)) {
                algorithmName = "d R' U2 R d' R U R'";
                ArrayList<Move> rules1 = MoveParser.parse("U");
                ArrayList<Move> rules2 = MoveParser.parse("R' U2 R U'");
                ArrayList<Move> rules3 = MoveParser.parse("R U R'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))
                    && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)) {
                    return goodFLLReport(rules1.size() + rules2.size() + rules3.size());
                }
                if (rules1.size() + rules2.size() + rules3.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size() + rules3.size();
                }
            }

            if (situationFLL(5, 27, 8, 28, 38)) {
                algorithmName = "U' R U2 R' d R' U' R";
                ArrayList<Move> rules1 = MoveParser.parse("U' R U2 R' U");
                ArrayList<Move> rules2 = MoveParser.parse("R' U' R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(5, 8, 38, 28, 27)) {
                algorithmName = "R U' R' U d R' U' R";
                ArrayList<Move> rules1 = MoveParser.parse("R U' R' U2");
                ArrayList<Move> rules2 = MoveParser.parse("R' U' R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(27, 37, 7, 8, 38)) {
                algorithmName = "F' U F U' d' F U F'";
                ArrayList<Move> rules1 = MoveParser.parse("F' U F U2");
                ArrayList<Move> rules2 = MoveParser.parse("F U F'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(1, 8, 38, 46, 27)) {
                algorithmName = "U F' U2 F U F' U2 F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(1, 8, 38, 46, 27)) {
                algorithmName = "U' R U2 R' U' R U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(3, 8, 38, 19, 27)) {
                algorithmName = "U F' U' F U F' U2 F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(27, 46, 1, 8, 38)) {
                algorithmName = "U' R U R' U' R U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            // 3. Corner pointing up, edge in top
            if (situationFLL(28, 38, 5, 27, 8)) {
                algorithmName = "R U2 R' U' R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(7, 38, 37, 27, 8)) {
                algorithmName = "F' U2 F U F' U' F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(46, 38, 1, 27, 8)) {
                algorithmName = "U R U2 R' U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(3, 38, 19, 27, 8)) {
                algorithmName = "U' F' U2 F U' F' U F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(19, 38, 3, 27, 8)) {
                algorithmName = "U2 R U R' U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(1, 38, 46, 27, 8)) {
                algorithmName = "U2 F' U' F U' F' U F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(37, 38, 7, 27, 8)) {
                algorithmName = "R U R' U' U' R U R' U' R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(5, 38, 28, 27, 8)) {
                algorithmName = "y' R' U' R U U R' U' R U R' U' R";
                ArrayList<Move> rules = MoveParser.parse("R' U' R U U R' U' R U R' U' R");
                if (chekStartWY(rules, start, ((4 + rotationWY - 1) % 4))) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            // 4. Corner in top, edge in middle
            if (situationFLL(8, 30, 38, 41, 27)) {
                algorithmName = "U F' U F U F' U2 F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(27, 30, 8, 41, 38)) {
                algorithmName = "U' R U' R' U' R U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(8, 41, 30, 38, 27)) {
                algorithmName = "U F' U' F d' F U F'";
                ArrayList<Move> rules1 = MoveParser.parse("U F' U' F U'");
                ArrayList<Move> rules2 = MoveParser.parse("F U F'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(27, 41, 30, 8, 38)) {
                algorithmName = "U' R U R' d U' R U R'";
                ArrayList<Move> rules1 = MoveParser.parse("U' R U R' U");
                ArrayList<Move> rules2 = MoveParser.parse("U' R U R'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(38, 41, 30, 27, 8)) {
                algorithmName = "R U' R' d R' U R";
                ArrayList<Move> rules1 = MoveParser.parse("R U' R' U");
                ArrayList<Move> rules2 = MoveParser.parse("R' U R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size() < minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(38, 30, 27, 41, 8)) {
                algorithmName = "R U R' U' R U R' U' R U R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            // 5. Corner in bottom, edge in top
            if (situationFLL(7, 33, 37, 44, 11)) {
                algorithmName = "U R U' R' U' F' U F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(28, 33, 5, 44, 11)) {
                algorithmName = "U' F' U F U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(7, 44, 37, 11, 33)) {
                algorithmName = "F' U F U' F' U F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(28, 11, 33, 5, 44)) {
                algorithmName = "R U' R' U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(28, 44, 11, 5, 33)) {
                algorithmName = "R U R' U' R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(7, 11, 33, 37, 44)) {
                algorithmName = "F' U' F U F' U' F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            // 6. Corner in bottom, edge in middle
            if (situationFLL(30, 44, 11, 41, 33)) {
                algorithmName = "R U' R' U R U2 R' U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(30, 11, 33, 41, 44)) {
                algorithmName = "R U' R' U' R U R' U' R U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodFLLReport(rules.size());
                }
                if (rules.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules.size();
                }
            }

            if (situationFLL(44, 41, 30, 11, 33)) {
                algorithmName = "R U R' U' R U' R' U d R' U' R";
                ArrayList<Move> rules1 = MoveParser.parse("R U R' U' R U' R' U2");
                ArrayList<Move> rules2 = MoveParser.parse("R' U' R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(11, 41, 30, 33, 44)) {
                algorithmName = "R U' R' d R' U' R U' R' U' R";
                ArrayList<Move> rules1 = MoveParser.parse("R U' R' U");
                ArrayList<Move> rules2 = MoveParser.parse("R' U' R U' R' U' R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }

            if (situationFLL(33, 41, 30, 44, 11)) {
                algorithmName = "R U' R' d R' U2 R U R' U2 R";
                ArrayList<Move> rules1 = MoveParser.parse("R U' R' U'");
                ArrayList<Move> rules2 = MoveParser.parse("R' U2 R U R' U2 R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))) {
                    return goodFLLReport(rules1.size() + rules2.size());
                }
                if (rules1.size() + rules2.size()< minLengths) {
                    algorithmNameSuggestion = algorithmName;
                    minLengths = rules1.size() + rules2.size();
                }
            }
        }
        boolean[] flags = {false, false, false, false};
        if (cube.getSticker(11) == 1 && cube.getSticker(44) == 4 && cube.getSticker(41) == 4
            && cube.getSticker(33) == 3 && cube.getSticker(30) == 3) {
            flags[0] = true;
        }
        if (cube.getSticker(17) == 1 && cube.getSticker(48) == 5 && cube.getSticker(51) == 5
                && cube.getSticker(32) == 3 && cube.getSticker(35) == 3) {
            flags[1] = true;
        }
        if (cube.getSticker(15) == 1 && cube.getSticker(50) == 5 && cube.getSticker(53) == 5
                && cube.getSticker(21) == 2 && cube.getSticker(24) == 2) {
            flags[2] = true;
        }
        if (cube.getSticker(9) == 1 && cube.getSticker(39) == 4 && cube.getSticker(42) == 4
                && cube.getSticker(23) == 2 && cube.getSticker(26) == 2) {
            flags[3] = true;
        }
        if (algorithmNameSuggestion.equals("No")) {
            if (up != null) {
                cube.rotate(up);
                Report.AnalysisError recursion = fLL(start + 1, up);
                start--;
                return new Report.AnalysisError(Report.StageName.F2L, start, recursion.getMoveEnd(),
                        moves.get(start).toString().concat(recursion.getActualMoves()), up.toString().concat(recursion.getSuggestion()));
            }
            Move newUp = new Move(Face.U, Turn.CLOCKWISE);
            if (moves.get(start).equals(new Move(Face.U, Turn.COUNTER_CLOCKWISE))) {
                newUp = new Move(Face.U, Turn.COUNTER_CLOCKWISE);
            }
            cube.rotate(newUp);
            Report.AnalysisError recursion = fLL(start + 1, newUp);
            start--;
            return new Report.AnalysisError(Report.StageName.F2L, start, recursion.getMoveEnd(),
                    moves.get(start).toString().concat(" ").concat(recursion.getActualMoves()), newUp.toString().concat(" ").concat(recursion.getSuggestion()));
        }
        int end = start;
        for (int i = start; i < moves.size(); i++) {
            cube.rotate(moves.get(i));
            if (!flags[0] && cube.getSticker(11) == 1 && cube.getSticker(44) == 4 && cube.getSticker(41) == 4
                    && cube.getSticker(33) == 3 && cube.getSticker(30) == 3) {
                break;
            }
            else if (!flags[1] && cube.getSticker(17) == 1 && cube.getSticker(48) == 5 && cube.getSticker(51) == 5
                    && cube.getSticker(32) == 3 && cube.getSticker(35) == 3) {
                break;
            }
            else if (!flags[2] && cube.getSticker(15) == 1 && cube.getSticker(50) == 5 && cube.getSticker(53) == 5
                    && cube.getSticker(21) == 2 && cube.getSticker(24) == 2) {
                break;
            }
            else if (!flags[3] && cube.getSticker(9) == 1 && cube.getSticker(39) == 4 && cube.getSticker(42) == 4
                    && cube.getSticker(23) == 2 && cube.getSticker(26) == 2) {
                break;
            }
            end++;
        }
        List<Move> sublist;
        if (end != moves.size()){
            sublist = moves.subList(start, end + 1);
        }
        else {
            sublist = moves.subList(start, end);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Move i : sublist) {
            stringBuilder.append(i.toString());
            stringBuilder.append(" ");
        }
        String movesString = stringBuilder.toString();
        return new Report.AnalysisError(Report.StageName.F2L, start, end, movesString, algorithmNameSuggestion);
    }

    private boolean situationOLL(int yellow0, int yellow1, int yellow2, int yellow3, int yellow4,
                                 int yellow5, int yellow6, int yellow7, int yellow8) {
        int yellow = index(0) / 9;
        return cube.getSticker(index(yellow0)) == yellow && cube.getSticker(index(yellow1)) == yellow
                && cube.getSticker(index(yellow2)) == yellow && cube.getSticker(index(yellow3)) == yellow
                && cube.getSticker(index(yellow4)) == yellow && cube.getSticker(index(yellow5)) == yellow
                && cube.getSticker(index(yellow6)) == yellow && cube.getSticker(index(yellow7)) == yellow
                && cube.getSticker(index(yellow8)) == yellow;
    }


    private Report.AnalysisError goodOLLReport (int ruleSize) {
        for (Move m : moves.subList(start, start + ruleSize)) {
            cube.rotate(m);
        }
        return new Report.AnalysisError(Report.StageName.OLL, start, start + ruleSize - 1,
                null, null);
    }

    public Report.AnalysisError oLL(int startIndex) {
        this.start = startIndex;
        String algorithmName = "No";
        for (int i = 0; i < 4; i++) {
            rotationWY = i;

            // Crosses
            if (situationOLL(1, 3, 4, 5, 7, 8, 20, 29, 47)) {
                algorithmName = "R' U2 R U R' U R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(1, 2, 3, 4, 5, 7, 18, 27, 36)) {
                algorithmName = "R U2 R' U' R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(1, 2, 3, 4, 5, 6, 7, 18, 27)) {
                algorithmName = "F' r U R' U' r' F R";
                ArrayList<Move> rules1 = MoveParser.parse("F' L");
                ArrayList<Move> rules2 = MoveParser.parse("U R' U' L'");
                ArrayList<Move> rules3 = MoveParser.parse("F R");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 1) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)) {
                    return goodOLLReport(rules1.size() + rules2.size() + rules3.size());
                }
            }

            if (situationOLL(1, 2, 3, 4, 5, 7, 8, 36, 47)) {
                algorithmName = "r U R' U' r' F R F'";
                ArrayList<Move> rules1 = MoveParser.parse("L");
                ArrayList<Move> rules2 = MoveParser.parse("U R' U' L'");
                ArrayList<Move> rules3 = MoveParser.parse("F R F'");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 1) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)) {
                    return goodOLLReport(rules1.size() + rules2.size() + rules3.size());
                }
            }

            if (situationOLL(1, 3, 4, 5, 7, 18, 38, 20, 45)) {
                algorithmName = "R U2 R2' U' R2 U' R2' U2 R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(1, 3, 4, 5, 7, 18, 27, 20, 29)) {
                algorithmName = "R U R' U R U' R' U R U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(0, 1, 2, 3, 4, 5, 7, 36, 29)) {
                algorithmName = "R2 D R' U2 R D' R' U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            // Dots
            if (situationOLL(4, 46, 37, 18, 19, 20, 27, 28, 29)) {
                algorithmName = "R U2 R' R' F R F' U2 R' F R F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(4, 18, 19, 20, 37, 38, 28, 46, 45)) {
                algorithmName = "F R U R' U' F' f R U R' U' f'";
                ArrayList<Move> rules1 = MoveParser.parse("F R U R' U' F' B");
                ArrayList<Move> rules2 = MoveParser.parse("R U R' U' B'");
                if (chek(rules1) && chekStartGB(rules2, start + rules1.size(), ((rotationGB + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(2, 4, 18, 19, 36, 37, 28, 27, 46)) {
                algorithmName = "f R U R' U' f' U F R U R' U' F'";
                ArrayList<Move> rules1 = MoveParser.parse("B");
                ArrayList<Move> rules2 = MoveParser.parse("R U R' U' B'");
                ArrayList<Move> rules3 = MoveParser.parse("U F R U R' U' F'");
                if (chek(rules1) && chekStartGB(rules2, start + rules1.size(), ((rotationRO + 1) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)) {
                    return goodOLLReport(rules1.size() + rules2.size() + rules3.size());
                }
            }

            if (situationOLL(4, 8, 37, 47, 48, 28, 29, 19, 20)) {
                algorithmName = "f R U R' U' f' U' F R U R' U' F'";
                ArrayList<Move> rules1 = MoveParser.parse("B");
                ArrayList<Move> rules2 = MoveParser.parse("R U R' U' B'");
                ArrayList<Move> rules3 = MoveParser.parse("U' F R U R' U' F'");
                if (chek(rules1) && chekStartGB(rules2, start + rules1.size(), ((rotationRO + 1) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)) {
                    return goodOLLReport(rules1.size() + rules2.size() + rules3.size());
                }
            }

            if (situationOLL(0, 2, 4, 37, 46, 19, 20, 27, 28)) {
                algorithmName = "M U R U R' U' M' R' F R F'";
                ArrayList<Move> rules11 = MoveParser.parse("R L'");
                ArrayList<Move> rules12 = MoveParser.parse("L' R");
                ArrayList<Move> rules21 = MoveParser.parse("U R U R' U' R' L");
                ArrayList<Move> rules22 = MoveParser.parse("U R U R' U' L R'");
                ArrayList<Move> rules3 = MoveParser.parse("R' F R F'");
                if ((chek(rules11) || chek(rules12)) && (chekStartRO(rules21, start + rules11.size(), ((rotationRO + 1) % 4))
                        || chekStartRO(rules22, start + rules11.size(), ((rotationRO + 1) % 4)))
                        && chekStartWY(rules3, start + rules11.size() + rules21.size(), rotationWY)) {
                    return goodOLLReport(rules11.size() + rules21.size() + rules3.size());
                }
            }

            if (situationOLL(45, 46, 47, 4, 6, 8, 19, 28, 37)) {
                algorithmName = "F R U R' U y' R' U2 R' F R F'";
                ArrayList<Move> rules1 = MoveParser.parse("F R U R' U");
                ArrayList<Move> rules2 = MoveParser.parse("R' U2 R' F R F'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((4 + rotationWY - 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(0, 4, 8, 37, 28, 19, 20, 45, 46)) {
                algorithmName = "R U R' U R' F R F' U2 R' F R F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(0, 2, 4, 6, 8, 46, 37, 19, 28)) {
                algorithmName = "M U R U R' U' M2 U R U' r'";
                ArrayList<Move> rules11 = MoveParser.parse("R L'");
                ArrayList<Move> rules12 = MoveParser.parse("L' R");
                ArrayList<Move> rules21 = MoveParser.parse("R U R' U' R2 L2");
                ArrayList<Move> rules22 = MoveParser.parse("R U R' U' L2 R2");
                ArrayList<Move> rules3 = MoveParser.parse("U R U' L'");
                if ((chek(rules11) || chek(rules12)) && (chekStartGB(rules21, start + rules11.size(), ((rotationRO + 1) % 4))
                        || chekStartRO(rules22, start + rules11.size(), ((rotationRO + 1) % 4)))
                        && chekStartRO(rules3, start + rules11.size() + rules21.size(), ((4 + rotationRO - 1) % 4))) {
                    return goodOLLReport(rules11.size() + rules21.size() + rules3.size());
                }
            }

            //All Corners
            if (situationOLL(0, 2, 3, 4, 5, 6, 8, 37, 46)) {
                algorithmName = "R U R' U' M' U R U' r'";
                ArrayList<Move> rules11 = MoveParser.parse("R U R' U' R' L");
                ArrayList<Move> rules12 = MoveParser.parse("R U R' U' L R'");
                ArrayList<Move> rules2 = MoveParser.parse("U R U' L'");
                if ((chek(rules11) || chek(rules12))
                        && chekStartRO(rules2, start + rules11.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules11.size() + rules2.size());
                }
            }


            if (situationOLL(0, 2, 3, 4, 6, 7, 8, 28, 46)) {
                algorithmName = "M' U' M U2' M' U' M";
                ArrayList<Move> rules11 = MoveParser.parse("R' L");
                ArrayList<Move> rules12 = MoveParser.parse("L R'");
                ArrayList<Move> rules21 = MoveParser.parse("U' L' R");
                ArrayList<Move> rules22 = MoveParser.parse("U' R L'");
                ArrayList<Move> rules31 = MoveParser.parse("U2 L R'");
                ArrayList<Move> rules32 = MoveParser.parse("U2 R' L");
                ArrayList<Move> rules41 = MoveParser.parse("U' L' R");
                ArrayList<Move> rules42 = MoveParser.parse("U' R L'");
                if ((chek(rules11) || chek(rules12))
                        && (chekStartRO(rules21, start + rules11.size(), ((rotationRO + 3) % 4))
                        || chekStartRO(rules22, start + rules11.size(), ((rotationRO + 3) % 4)))
                        && (chekStartWY(rules31, start + rules11.size() + rules21.size(), rotationWY)
                        || chekStartWY(rules32, start + rules11.size() + rules21.size(), rotationWY))
                        && (chekStartRO(rules41, start + rules11.size() + rules21.size() + rules31.size(), ((rotationRO + 3) % 4))
                        || chekStartRO(rules42, start + rules11.size() + rules21.size() + rules31.size(), ((rotationRO + 3) % 4)))) {
                    return goodOLLReport(rules11.size() + rules21.size() + rules31.size() + rules41.size());
                }
            }

            // Lines
            if (situationOLL(18, 19, 20, 1, 4, 7, 27, 28, 29)) {
                algorithmName = "R U2 R2 U' R U' R' U2 F R F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(47, 19, 36, 1, 4, 7, 27, 28, 29)) {
                algorithmName = "R U R' U R d' R U' R' F'";
                ArrayList<Move> rules1 = MoveParser.parse("R U R' U R U'");
                ArrayList<Move> rules2 = MoveParser.parse("R U' R' F'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(18, 20, 3, 4, 5, 45, 46, 37, 38)) {
                algorithmName = "f R U R' U' R U R' U' f'";
                ArrayList<Move> rules1 = MoveParser.parse("D");
                ArrayList<Move> rules2 = MoveParser.parse("R U R' U' R U R' U' D'");
                if (chek(rules1) && chekStartGB(rules2, start + rules1.size(), ((rotationGB + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(18, 20, 3, 4, 5, 29, 46, 37, 27)) {
                algorithmName = "F R U R' U' R F' r U R' U' r'";
                ArrayList<Move> rules1 = MoveParser.parse("F R U R' U' R F' L");
                ArrayList<Move> rules2 = MoveParser.parse("U R' U' L'");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            // Ts
            if (situationOLL(18, 20, 3, 4, 5, 2, 8, 46, 37)) {
                algorithmName = "F R U R' U' F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(47, 36, 3, 4, 5, 2, 8, 46, 37)) {
                algorithmName = "R U R' U' R' F R F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            // Zs
            if (situationOLL(20, 45, 46, 0, 3, 4, 5, 8, 37)) {
                algorithmName = "R' F R U R' U' F' U R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(6, 47, 46, 2, 3, 4, 5, 27, 37)) {
                algorithmName = "L F' L' U' L U F U' L'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            // Big Ls
            if (situationOLL(36, 45, 46, 18, 3, 4, 5, 8, 37)) {
                algorithmName = "R' F R U R' F' R y' R U' R'";
                ArrayList<Move> rules1 = MoveParser.parse("R' F R U R' F' R");
                ArrayList<Move> rules2 = MoveParser.parse("R U' R'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(47, 46, 29, 3, 4, 5, 6, 37, 38)) {
                algorithmName = "F U R U' R2 F' R U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(18, 46, 2, 3, 4, 5, 27, 36, 37)) {
                algorithmName = "r U r' R U R' U' r U' r'";
                ArrayList<Move> rules1 = MoveParser.parse("L");
                ArrayList<Move> rules2 = MoveParser.parse("U L'");
                ArrayList<Move> rules3 = MoveParser.parse("R U R' U' L");
                ArrayList<Move> rules4 = MoveParser.parse("U' L'");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)
                        && chekStartRO(rules4, start + rules1.size() + rules2.size() + rules3.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size() + rules3.size() + rules4.size());
                }
            }

            if (situationOLL(0, 46, 29, 3, 4, 5, 38, 20, 37)) {
                algorithmName = "l' U' l (L' U' L U) l' U l";
                ArrayList<Move> rules1 = MoveParser.parse("R'");
                ArrayList<Move> rules2 = MoveParser.parse("U' R");
                ArrayList<Move> rules3 = MoveParser.parse("L' U L U' R'");
                ArrayList<Move> rules4 = MoveParser.parse("U R");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)
                        && chekStartRO(rules4, start + rules1.size() + rules2.size() + rules3.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size() + rules3.size() + rules4.size());
                }
            }

            // Cs
            if (situationOLL(0, 1, 4, 6, 7, 19, 27, 28, 29)) {
                algorithmName = "R' U' R' F R F' U R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(18, 46, 29, 3, 4, 5, 6, 8, 37)) {
                algorithmName = "R U R' U' x D' R' U R U' D x'";
                ArrayList<Move> rules1 = MoveParser.parse("R U R' U'");
                ArrayList<Move> rules2 = MoveParser.parse("D' R' U R U' D");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            //Ws
            if (situationOLL(47, 1, 2, 3, 4, 6, 28, 27, 37)) {
                algorithmName = "R U R' U R U' R' U' R' F R F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(0, 1, 45, 19, 4, 20, 8, 5, 37)) {
                algorithmName = "L' U' L U' L' U L U L F' L' F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            // Ps
            if (situationOLL(18, 20, 19, 46, 2, 4, 5, 7, 8)) {
                algorithmName = "f R U R' U' f'";
                ArrayList<Move> rules1 = MoveParser.parse("D");
                ArrayList<Move> rules2 = MoveParser.parse("R U R' U' D'");
                if (chek(rules1) && chekStartGB(rules2, start + rules1.size(), ((rotationGB + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(0, 3, 4, 6, 7, 45, 27, 28, 29)) {
                algorithmName = "f' L' U' L U f";
                ArrayList<Move> rules1 = MoveParser.parse("D'");
                ArrayList<Move> rules2 = MoveParser.parse("L' U' L U D");
                if (chek(rules1) && chekStartGB(rules2, start + rules1.size(), ((rotationGB + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(1, 2, 4, 5, 8, 47, 19, 36, 37)) {
                algorithmName = "R' U' F U R U' R' F' R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(0, 3, 6, 1, 4, 45, 28, 37, 38)) {
                algorithmName = "F U R U' F' r U R' U' r'";
                ArrayList<Move> rules1 = MoveParser.parse("F U R U' F' L");
                ArrayList<Move> rules2 = MoveParser.parse("U R' U' L'");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            // Squares
            if (situationOLL(0, 4, 5, 7, 8, 19, 46, 29, 36)) {
                algorithmName = "R U2 R' R' F R F' R U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(0, 1, 3, 4, 8, 28, 29, 36, 37)) {
                algorithmName = "F R' F' R U R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(4, 5, 7, 8, 19, 20, 46, 47, 29)) {
                algorithmName = "r' U2 R U R' U r";
                ArrayList<Move> rules1 = MoveParser.parse("L'");
                ArrayList<Move> rules2 = MoveParser.parse("U2 R U R' U L");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(18, 19, 1, 2, 4, 5, 27, 36, 37)) {
                algorithmName = "r U2 R' U' R U' r'";
                ArrayList<Move> rules1 = MoveParser.parse("L");
                ArrayList<Move> rules2 = MoveParser.parse("U2 R' U' R U' L'");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            // Little Ls
            if (situationOLL(18, 20, 1, 3, 4, 45, 28, 37, 38)) {
                algorithmName = "F R U R' U' R U R' U' F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(19, 47, 1, 4, 5, 27, 29, 36, 37)) {
                algorithmName = "F' L' U' L U L' U' L U F";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(47, 1, 3, 4, 27, 28, 29, 36, 37)) {
                algorithmName = "R' F R2 B' R2' F' R2 B R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(18, 19, 20, 1, 4, 5, 45, 37, 38)) {
                algorithmName = "R' F R' F' R2 U2 y R' F R F'";
                ArrayList<Move> rules1 = MoveParser.parse("R' F R' F' R2 U2");
                ArrayList<Move> rules2 = MoveParser.parse("R' F R F'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(1, 3, 4, 18, 20, 37, 27, 28, 29)) {
                algorithmName = "l' U' L U' L' U L U' L' U2 l";
                ArrayList<Move> rules1 = MoveParser.parse("R'");
                ArrayList<Move> rules2 = MoveParser.parse("U' L U' L' U L U' L' U2 R");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(18, 19, 20, 1, 4, 5, 27, 29, 37)) {
                algorithmName = "r U R' U R U' R' U R U2' r'";
                ArrayList<Move> rules1 = MoveParser.parse("L");
                ArrayList<Move> rules2 = MoveParser.parse("U R' U R U' R' U R U2' L'");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            // Other shapes
            if (situationOLL(0, 3, 4, 7, 20, 46, 28, 29, 38)) {
                algorithmName = "F' L' U' L U F y F R U R' U' F'";
                ArrayList<Move> rules1 = MoveParser.parse("F' L' U' L U F");
                ArrayList<Move> rules2 = MoveParser.parse("F R U R' U' F'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(18, 19, 46, 2, 4, 5, 7, 27, 36)) {
                algorithmName = "F R U R' U' F' U F R U R' U' F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(47, 1, 3, 4, 6, 28, 29, 37, 38)) {
                algorithmName = "r U R' U R U2 r'";
                ArrayList<Move> rules1 = MoveParser.parse("L");
                ArrayList<Move> rules2 = MoveParser.parse("U R' U R U2 L'");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(0, 3, 4, 7, 45, 46, 27, 28, 36)) {
                algorithmName = "r' U' R U' R' U2 r";
                ArrayList<Move> rules1 = MoveParser.parse("L'");
                ArrayList<Move> rules2 = MoveParser.parse("U' R U' R' U2 L");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationOLL(2, 3, 4, 7, 20, 46, 47, 28, 38)) {
                algorithmName = "R U R' U R' F R F' R U2 R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(1, 3, 4, 8, 28, 18, 45, 36, 37)) {
                algorithmName = "R U R' U' R' F R2 U R' U' F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(1, 2, 3, 4, 8, 47, 28, 36, 37)) {
                algorithmName = "R U R' U' R U' R' F' U' F R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(18, 19, 1, 2, 3, 4, 8, 28, 37)) {
                algorithmName = "R' F R F' R' F R F' R U R' U' R U R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(1, 3, 4, 6, 8, 47, 28, 45, 37)) {
                algorithmName = "R U R' U R U2 R' F R U R' U' F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules)) {
                    return goodOLLReport(rules.size());
                }
            }

            if (situationOLL(18, 1, 3, 4, 6, 8, 28, 29, 37)) {
                algorithmName = "L F' L' F L' U2 L d R U R'";
                ArrayList<Move> rules1 = MoveParser.parse("L F' L' F L' U2 L U");
                ArrayList<Move> rules2 = MoveParser.parse("R U R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))) {
                    return goodOLLReport(rules1.size() + rules2.size());
                }
            }
        }
        int end = start;
        for (int i = start; i < moves.size(); i++) {
            cube.rotate(moves.get(i));
            if (cube.isOLLSolved()) {
                break;
            }
            end++;
        }
        List<Move> sublist;
        if (end != moves.size()){
            sublist = moves.subList(start, end + 1);
        }
        else {
            sublist = moves.subList(start, end);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Move i : sublist) {
            stringBuilder.append(i.toString());
            stringBuilder.append(" ");
        }
        String movesString = stringBuilder.toString();
        return new Report.AnalysisError(Report.StageName.OLL, start, end, movesString, algorithmName);
    }

    private Report.AnalysisError goodPLLReport (int ruleSize) {
        return new Report.AnalysisError(Report.StageName.PLL, start, start + ruleSize - 1,
                null, null);
    }

    private boolean situationPLL(int index36, int index37, int index38, int index27, int index28, int index29,
                                 int index45, int index46, int index47, int index18,int index19, int index20) {
        return cube.getSticker(index(36)) == index36 && cube.getSticker(index(37)) == index37
                && cube.getSticker(index(38)) == index38 && cube.getSticker(index(27)) == index27
                && cube.getSticker(index(28)) == index28 && cube.getSticker(index(29)) == index29
                && cube.getSticker(index(45)) == index45 && cube.getSticker(index(46)) == index46
                && cube.getSticker(index(47)) == index47 && cube.getSticker(index(18)) == index18
                && cube.getSticker(index(19)) == index19 && cube.getSticker(index(20)) == index20;
    }

    private boolean checkPLL(int index) {
        for (Move m : moves.subList(start, index)) {
            cube.rotate(m);
        }
        if (cube.isPLLSolved()) {
            return true;
        }
        int green = index(36) / 9;
        if (cube.getSticker(index(27)) == green) {
            lastPLL = new Move(Face.U, Turn.CLOCKWISE);
        } else if (cube.getSticker(index(45)) == green) {
            lastPLL = new Move(Face.U, Turn.DOUBLE);
        } else {
            lastPLL = new Move(Face.U, Turn.COUNTER_CLOCKWISE);
        }
        if (index < moves.size()) {
            Move last = moves.get(index);
            if (last == lastPLL) {
                cube.rotate(last);
                return true;
            }
            start = index + 1;
            return false;
        }
        start = index;
        return false;
    }

    public Report.AnalysisError pLL(int startIndex) {
        start = startIndex;
        String algorithmName = "No";
        for (int i = 0; i < 4; i++) {
            rotationWY = i;
            int red = index(18) / 9;
            int green = index(36) / 9;
            int orange = index(27) / 9;
            int blue = index(45) / 9;

            // Edges only
            if (situationPLL(blue, green, blue, red, orange, red, green, blue, green, orange, red, orange)) {
                algorithmName = "M2 U M2 U2 M2 U M2";
                ArrayList<Move> rules11 = MoveParser.parse("R2 L2");
                ArrayList<Move> rules12 = MoveParser.parse("L2 R2");
                ArrayList<Move> rules21 = MoveParser.parse("U R2 L2");
                ArrayList<Move> rules22 = MoveParser.parse("U L2 R2");
                ArrayList<Move> rules31 = MoveParser.parse("U2 R2 L2");
                ArrayList<Move> rules32 = MoveParser.parse("U2 L2 R2");
                if ((chek(rules11) || chek(rules12)) && (chekStartGB(rules21, start + rules11.size(), ((rotationRO + 2) % 4))
                        || chekStartGB(rules22, start + rules11.size(), ((rotationRO + 2) % 4)))
                        && (chekStartGB(rules31, start + rules11.size() + rules21.size(), rotationRO)
                        || chekStartGB(rules32, start + rules11.size() + rules21.size(), rotationRO))
                        && (chekStartGB(rules21, start + rules11.size() + rules21.size() + rules31.size(), ((rotationRO + 2) % 4))
                        || chekStartGB(rules22, start + rules11.size() + rules21.size() + rules31.size(), ((rotationRO + 2) % 4)))
                        && checkPLL(start + rules11.size() + 2 * rules22.size() + rules31.size())) {
                    return goodPLLReport(rules11.size() + 2 * rules21.size() + rules31.size());
                }
            }

            if (situationPLL(blue, red, blue, red, blue, red, green, orange, green, orange, green, orange)) {
                algorithmName = "R' U' R2 U R U R' U' R U R U' R U' R' U2";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, blue, blue, red, green, red, green, orange, green, orange, red, green)) {
                algorithmName = "R2 U' R' U' R U R U R U' R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, blue, blue, red, orange, red, green, orange, green, orange, green, orange)) {
                algorithmName = "R' U R' U' R' U' R' U R U R2";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            // Corners only
            if (situationPLL(blue, blue, green, orange, red, blue, red, green, red, green, orange, orange)) {
                algorithmName = "x z' R2 U2 R' D' R U2 R' D R' z x'";
                ArrayList<Move> rules = MoveParser.parse("R2 U2 R' D' R U2 R' D R'");
                rotationWY = (rotationWY + 1) % 4;
                if (chekStartRO(rules, start, ((rotationRO + 3) % 4))) {
                    rotationWY = (rotationWY + 3) % 4;
                    if (checkPLL(start + rules.size())) {
                        return goodPLLReport(rules.size());
                    }
                }
            }

            if (situationPLL(blue, blue, red, green, red, green, orange, green, blue, red, orange, orange)) {
                algorithmName = "x R2 D2 R U R' D2 R U' R x'";
                ArrayList<Move> rules = MoveParser.parse("R2 D2 R U R' D2 R U' R");
                if (chekStartRO(rules, start, ((rotationRO + 3) % 4)) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(red, blue, orange, blue, red, green, orange, green, red, green, orange, blue)) {
                algorithmName = "R2 U R' U' y R U R' U' R U R' U' R U R' y' R U' R2'";
                ArrayList<Move> rules1 = MoveParser.parse("R2 U R' U'");
                ArrayList<Move> rules2 = MoveParser.parse("R U R' U' R U R' U' R U R'");
                ArrayList<Move> rules3 = MoveParser.parse("R U' R2'");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)
                        && checkPLL(start + rules1.size() + rules2.size() + rules3.size())) {
                    return goodPLLReport(rules1.size() + rules2.size() + rules3.size());
                }
            }

            // Edges and corners
            if (situationPLL(blue, blue, red, green, orange, blue, red, green, green, orange, red, orange)) {
                algorithmName = "R U R' U' R' F R2 U' R' U' R U R' F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, blue, green, orange, red, red, green, orange, blue, red, green, orange)) {
                algorithmName = "F R U' R' U' R U R' F' R U R' U' R' F R F'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, green, red, green, red, blue, red, blue, green, orange, orange, orange)) {
                algorithmName = "U' R' U R U' R2 F' U' F U x R U R' U' R2 x'";
                ArrayList<Move> rules1 = MoveParser.parse("U' R' U R U' R2 F' U' F U");
                ArrayList<Move> rules2 = MoveParser.parse("R U R' U' R2");
                if (chek(rules1) && chekStartRO(rules2, start + rules1.size(), ((rotationRO + 3) % 4))
                        && checkPLL(start + rules1.size() + rules2.size())) {
                    return goodPLLReport(rules1.size() + rules2.size());
                }
            }

            if (situationPLL(blue, blue, green, orange, green, red, green, red, blue, red, orange, orange)) {
                algorithmName = "R' U R' U' y R' D R' D' R2 y' R' B' R B R";
                ArrayList<Move> rules1 = MoveParser.parse("R' U R' U'");
                ArrayList<Move> rules2 = MoveParser.parse("R' D R' D'");
                ArrayList<Move> rules3 = MoveParser.parse("R' B' R B R");
                if (chek(rules1) && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)
                        && checkPLL(start + rules1.size() + rules2.size() + rules3.size())) {
                    return goodPLLReport(rules1.size() + rules2.size() + rules3.size());
                }
            }

            if (situationPLL(orange, orange, green, red, red, red, green, green, orange, blue, blue, green)) {
                algorithmName = "L' U' L F L' U' L U L F' L2 U L U";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, red, red, green, blue, blue, red, green, green, orange, orange, orange)) {
                algorithmName = "R U R' F' R U R' U' R' F R2 U' R' U'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, orange, blue, red, red, green, orange, green, red, green, blue, orange)) {
                algorithmName = "L U2 L' U2 L F' L' U' L U L F L2 U";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, red, blue, red, blue, green, orange, green, red, green, orange, orange)) {
                algorithmName = "R' U2 R U2 R' F R U R' U' R' F' R2 U'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(green, blue, blue, red, orange, orange, blue, green, green, orange, red, red)) {
                algorithmName = "R U R' U R U R' F' R U R' U' R' F R2 U' R' U2 R U' R'";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(blue, blue, green, orange, orange, red, green, green, blue, red, red, orange)) {
                algorithmName = "R' U R U' R' F' U' F R U R' F R' F' R U' R";
                ArrayList<Move> rules = MoveParser.parse(algorithmName);
                if (chek(rules) && checkPLL(start + rules.size())) {
                    return goodPLLReport(rules.size());
                }
            }

            if (situationPLL(orange, green, orange, blue, red, red, green, orange, blue, red, blue, green)) {
                algorithmName = "y R2 u R' U R' U' R u' R2 y' R' U R";
                ArrayList<Move> rules1 = MoveParser.parse("R2 B");
                ArrayList<Move> rules2 = MoveParser.parse("R' U R' U' R B'");
                ArrayList<Move> rules3 = MoveParser.parse("R2");
                ArrayList<Move> rules4 = MoveParser.parse("R' U R");
                if (chekStartWY(rules1, start, ((rotationWY + 1) % 4))
                        && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 2) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), ((rotationWY + 1) % 4))
                        && chekStartWY(rules4, start + rules1.size() + rules2.size() + rules3.size(), rotationWY)
                        && checkPLL(start + rules1.size() + rules2.size() + rules3.size() + rules4.size())) {
                    return goodPLLReport(rules1.size() + rules2.size() + rules3.size() + rules4.size());
                }
            }

            if (situationPLL(blue, green, red, green, blue, blue, red, orange, green, orange, red, orange)) {
                algorithmName = "R' U' R y R2 u R' U R U' R u' R2";
                ArrayList<Move> rules1 = MoveParser.parse("R2 B");
                ArrayList<Move> rules2 = MoveParser.parse("R' U R' U' R B'");
                ArrayList<Move> rules3 = MoveParser.parse("R2");
                ArrayList<Move> rules4 = MoveParser.parse("R' U R");
                if (chekStartWY(rules1, start + rules4.size(), ((rotationWY + 1) % 4))
                        && chekStartWY(rules2, start + rules4.size(), ((rotationWY + 2) % 4))
                        && chekStartWY(rules3, start + rules4.size() + rules2.size(), ((rotationWY + 1) % 4))
                        && chekStartWY(rules4, start, rotationWY)
                        && checkPLL(start + rules1.size() + rules2.size() + rules3.size() + rules4.size())) {
                    return goodPLLReport(rules1.size() + rules2.size() + rules3.size() + rules4.size());
                }
            }

            if (situationPLL(orange, blue, orange, blue, green, red, green, orange, blue, red, red, green)) {
                algorithmName = "y R2' u' R U' R U R' u R2 y R U' R'";
                ArrayList<Move> rules1 = MoveParser.parse("R2 B'");
                ArrayList<Move> rules2 = MoveParser.parse("R' U R' U' R B");
                ArrayList<Move> rules3 = MoveParser.parse("R2");
                ArrayList<Move> rules4 = MoveParser.parse("R' U R");
                if (chekStartWY(rules1, start, ((rotationWY + 1) % 4))
                        && chekStartWY(rules2, start + rules1.size(), rotationWY)
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), ((rotationWY + 1) % 4))
                        && chekStartWY(rules4, start + rules1.size() + rules2.size() + rules3.size(), rotationWY)
                        && checkPLL(start + rules1.size() + rules2.size() + rules3.size() + rules4.size())) {
                    return goodPLLReport(rules1.size() + rules2.size() + rules3.size() + rules4.size());
                }
            }

            if (situationPLL(red, blue, green, orange, red, orange, blue, orange, red, green, green, blue)) {
                algorithmName = "y2 R U R' y' R2 u' R U' R' U R' u R2";
                ArrayList<Move> rules1 = MoveParser.parse("R U R'");
                ArrayList<Move> rules2 = MoveParser.parse("R2 B'");
                ArrayList<Move> rules3 = MoveParser.parse("R U' R' U R' B");
                ArrayList<Move> rules4 = MoveParser.parse("R2");
                if (chekStartWY(rules1, start, ((rotationWY + 2) % 4))
                        && chekStartWY(rules2, start + rules1.size(), ((rotationWY + 1) % 4))
                        && chekStartWY(rules3, start + rules1.size() + rules2.size(), rotationWY)
                        && chekStartWY(rules4, start + rules1.size() + rules2.size() + rules3.size(), ((rotationWY + 1) % 4))
                        && checkPLL(start + rules1.size() + rules2.size() + rules3.size() + rules4.size())) {
                    return goodPLLReport(rules1.size() + rules2.size() + rules3.size() + rules4.size());
                }
            }
        }

        int end = startIndex;
        for (int i = start; i < moves.size(); i++) {
            cube.rotate(moves.get(i));
            if (cube.isPLLSolved()) {
                break;
            }
            end++;
        }
        List<Move> sublist;
        if (end != moves.size()){
            sublist = moves.subList(startIndex, end + 1);
        }
        else {
            sublist = moves.subList(startIndex, end);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Move i : sublist) {
            stringBuilder.append(i.toString());
            stringBuilder.append(" ");
        }
        String movesString = stringBuilder.toString();
        if (lastPLL != null) {
            algorithmName = algorithmName + lastPLL;
        }
        return new Report.AnalysisError(Report.StageName.PLL, startIndex, end, movesString, algorithmName);
    }
}