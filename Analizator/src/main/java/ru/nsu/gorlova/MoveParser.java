package ru.nsu.gorlova;

import java.util.ArrayList;

public class MoveParser {
    public static ArrayList<Move> parse(String notation) {
        ArrayList<Move> moves = new ArrayList<>();
        if (notation == null || notation.isBlank()) return moves;
        String[] tokens = notation.trim().split("\\s+");
        for (String token : tokens) {
            if (token.isEmpty()) continue;

            Face face = Face.valueOf(token.substring(0, 1).toUpperCase());
            Turn turn = Turn.CLOCKWISE;

            if (token.length() > 1) {
                char modChar = token.charAt(1);
                if (modChar == '\'') turn = Turn.COUNTER_CLOCKWISE;
                else if (modChar == '2') turn = Turn.DOUBLE;
            }
            moves.add(new Move(face, turn));
        }
        return moves;
    }
}