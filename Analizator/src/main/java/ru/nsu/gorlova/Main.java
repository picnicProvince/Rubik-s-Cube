package ru.nsu.gorlova;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        /*
        CubeState cube = new CubeState();
        cube.rotate(new Move(Face.D, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.U, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.L, Turn.DOUBLE));
        cube.rotate(new Move(Face.R, Turn.DOUBLE));
        cube.rotate(new Move(Face.F, Turn.CLOCKWISE));
        //
        cube.rotate(new Move(Face.D, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.B, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.L, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.R, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.U, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.F, Turn.CLOCKWISE));
        //
        cube.rotate(new Move(Face.D, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.B, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.L, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.R, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.U, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.F, Turn.CLOCKWISE));
        //
        cube.rotate(new Move(Face.D, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.B, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.L, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.R, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.U, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.F, Turn.CLOCKWISE));

        cube.rotate(new Move(Face.U, Turn.DOUBLE));
        cube.rotate(new Move(Face.B, Turn.DOUBLE));
        cube.rotate(new Move(Face.L, Turn.DOUBLE));
        cube.rotate(new Move(Face.F, Turn.COUNTER_CLOCKWISE));
        cube.rotate(new Move(Face.L, Turn.DOUBLE));
        cube.rotate(new Move(Face.B, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.U, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.B, Turn.COUNTER_CLOCKWISE));
        cube.rotate(new Move(Face.R, Turn.DOUBLE));
        //System.out.print(cube.isCrossSolved());
        cube.rotate(new Move(Face.B, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.U, Turn.CLOCKWISE));
        cube.rotate(new Move(Face.B, Turn.COUNTER_CLOCKWISE));
        //cube.print();
        // (U F' U' F) (d' F U F')
        ArrayList<Move> moves = MoveParser.parse("U F' U' F U' R U R' F F F");
        CFOP cfop = new CFOP(cube, moves);
        Report.AnalysisError answer = cfop.fLL(0, null);
        System.out.printf("%s", answer.suggestion());
        System.out.printf("%d", answer.moveEnd());
        */



        // y-0, w-1, r-2, o-3, g-4, b-5
        /*
        int[] st = {4, 5, 2, 5, 0, 3, 1, 4, 0, 2, 1, 5, 1, 1, 1, 1, 1, 1, 3, 2, 5, 4, 2, 0, 2, 2, 4, 3, 4, 4, 3, 3, 5, 2, 3, 5, 3, 2, 4, 2, 4, 0, 1, 4, 0, 0, 3, 1, 0, 5, 0, 3, 5, 5};
        CubeState cube = new CubeState();
        cube.setStickers(st);
        cube.print();
        ArrayList<Move> moves = MoveParser.parse("U' B' U B U L U' L' R U2 R' U R U' R' U' F' U F B B B B");
        CFOP cfop = new CFOP(cube, moves);
        Report.AnalysisError answer = cfop.fLL(0, null);
        System.out.printf("%s%n", answer.suggestion());
        Report.AnalysisError answerr = cfop.fLL(answer.moveEnd() + 1, null);
        System.out.printf("%s%n", answerr.suggestion());
        System.out.printf("%s%n%n", answerr.actualMoves());
        Report.AnalysisError answerrr = cfop.fLL(answerr.moveEnd() + 1, null);
        System.out.printf("%s%n", answerrr.suggestion());
        System.out.printf("%s%n", answerrr.actualMoves());
        */

        Analyzer analyzer = new Analyzer();
        Report report = analyzer.analyze("U R2 F D' L U2 R'", "L' F' L U2 R2 D2 R' D2 L2 U B2 U' L2 L' U L F U' F' U' L' U L U F'");
        System.out.printf("%s%n",report.toString());
        CubeState cube = new CubeState();
        ArrayList<Move> moves = MoveParser.parse("U R2 F D' L U2 R'");
        for (Move m : moves) {
            cube.rotate(m);
        }
        cube.print();
    }
}