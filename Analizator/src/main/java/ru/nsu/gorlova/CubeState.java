package ru.nsu.gorlova;

/**
 * Математическая модель кубика Рубика.
 * Состояние хранится как массив из 54 стикеров.
 */
public class CubeState {

    private static final int STICKERS_COUNT = 54;
    private int[] stickers = new int[STICKERS_COUNT];

    public CubeState() {
        reset();
    }

    /*
          [0] [1] [2]      <- Грань U (Верх, индексы 0-8). Центр = [4]
          [3] [4] [5]
          [6] [7] [8]

[18][19][20] [36][37][38] [27][28][29] [45][46][47]  <- Грани L, F, R, B
[21][22][23] [39][40][41] [30][31][32] [48][49][50]     (индексы 18-53)
[24][25][26] [42][43][44] [33][34][35] [51][52][53]

          [9] [10][11]     <- Грань D (Низ, индексы 9-17). Центр = [13]
          [12][13][14]
          [15][16][17]
     */
    public void reset() {
        for (int i = 0; i < STICKERS_COUNT; i++) {
            stickers[i] = i / 9;
        }
    }

    /**
     * Количество поворотов.
     */
    public void rotate(Move move) {
        if (move == null) {
            return;
        }
        for (int t = 0; t <= move.turn().ordinal(); t++) {
            rotateClockwise(move.face());
        }
    }

    /**
     * Циклический поворот грани по часовой стрелке на 90°.
     * Реализуется как две независимые перестановки:
     * 1) 8 стикеров самой грани (3x3 кольцо вокруг центра)
     * 2) 12 стикеров примыкающих рёбер соседних граней
     */
    private void rotateClockwise(Face face) {
        int[] upCornerCycle;
        int[] rightCornerCycle;
        int[] leftCornerCycle;
        int[] upEdgeCycle;
        int[] downEdgeCycle;

        switch (face) {
            case U -> {
                upCornerCycle = new int[]{0, 2, 8, 6};
                rightCornerCycle = new int[]{38, 20, 47, 29};
                leftCornerCycle = new int[]{36, 18, 45, 27};
                upEdgeCycle = new int[]{1, 5, 7, 3};
                downEdgeCycle = new int[]{46, 28, 37, 19};
            }
            case D -> {
                upCornerCycle = new int[]{9, 11, 17, 15};
                rightCornerCycle = new int[]{35, 53, 26, 44};
                leftCornerCycle = new int[]{33, 51, 24, 42};
                upEdgeCycle = new int[]{10, 14, 16, 12};
                downEdgeCycle = new int[]{34, 52, 25, 43};
            }
            case L -> {
                upCornerCycle = new int[]{18, 20, 26, 24};
                rightCornerCycle = new int[]{6, 42, 15, 47};
                leftCornerCycle = new int[]{0, 36, 9, 53};
                upEdgeCycle = new int[]{19, 23, 25, 21};
                downEdgeCycle = new int[]{3, 39, 12, 50};
            }
            case R -> {
                upCornerCycle = new int[]{27, 29, 35, 33};
                rightCornerCycle = new int[]{8, 45, 17, 44};
                leftCornerCycle = new int[]{2, 51, 11, 38};
                upEdgeCycle = new int[]{28, 32, 34, 30};
                downEdgeCycle = new int[]{5, 48, 14, 41};
            }
            case F -> {
                upCornerCycle = new int[]{36, 38, 44, 42};
                rightCornerCycle = new int[]{6, 27, 11, 26};
                leftCornerCycle = new int[]{8, 33, 9, 20};
                upEdgeCycle = new int[]{37, 41, 43, 39};
                downEdgeCycle = new int[]{7, 30, 10, 23};
            }
            case B -> {
                upCornerCycle = new int[]{45, 47, 53, 51};
                rightCornerCycle = new int[]{2, 18, 15, 35};
                leftCornerCycle = new int[]{0, 24, 17, 29};
                upEdgeCycle = new int[]{46, 50, 52, 48};
                downEdgeCycle = new int[]{16, 32, 1, 21};
            }
            default -> throw new IllegalStateException();
        }

        cycle(upCornerCycle);
        cycle(rightCornerCycle);
        cycle(leftCornerCycle);
        cycle(upEdgeCycle);
        cycle(downEdgeCycle);
    }

    /**
     * Циклический сдвиг: a -> b -> c -> d -> a.
     */
    private void cycle(int[] idx) {
        int tmp = stickers[idx[3]];
        stickers[idx[3]] = stickers[idx[2]];
        stickers[idx[2]] = stickers[idx[1]];
        stickers[idx[1]] = stickers[idx[0]];
        stickers[idx[0]] = tmp;
    }

    public boolean isCrossSolved() {
        return stickers[16] == 1 && stickers[12] == 1 && stickers[10] == 1
                && stickers[14] == 1 && stickers[43] == 4 && stickers[34] == 3
                && stickers[52] == 5 && stickers[25] == 2;
    }

    public boolean isFLLSolved() {
        for (int i = 9; i < 18; i++) {
            if (stickers[i] != 1) return false;
        }
        for (int i = 21; i < 27; i++) {
            if (stickers[i] != 2 || stickers[i + 9] != 3
                || stickers[i + 18] != 4 || stickers[i + 27] != 5) {
                return false;
            }
        }
        return true;
    }

    public boolean isOLLSolved() {
        if (!isFLLSolved()) return false;
        for (int i = 0; i < 9; i++) {
            if (stickers[i] != 0) return false;
        }
        return true;
    }

    public boolean isPLLSolved() {
        for (int i = 0; i < STICKERS_COUNT; i++) {
            if (stickers[i] != i / 9) return false;
        }
        return true;
    }

    public int[] getStickersCopy() {
        return java.util.Arrays.copyOf(stickers, stickers.length);
    }

    public int getSticker(int index) {
        return stickers[index];
    }

    public void setStickers(int[] st) {
        stickers = st;
    }

    private char color(int face) {
        switch (face){
            case 0 -> {
                return 'Y';
            }
            case 1 -> {
                return 'W';
            }
            case 2 -> {
                return 'R';
            }
            case 3 -> {
                return 'O';
            }
            case 4 -> {
                return 'G';
            }
            case 5 -> {
                return 'B';
            }
        }
        return ' ';
    }
    /*
      [0] [1] [2]      <- Грань U (Верх, индексы 0-8). Центр = [4]
      [3] [4] [5]
      [6] [7] [8]

[18][19][20] [36][37][38] [27][28][29] [45][46][47]  <- Грани L, F, R, B
[21][22][23] [39][40][41] [30][31][32] [48][49][50]     (индексы 18-53)
[24][25][26] [42][43][44] [33][34][35] [51][52][53]

      [9] [10][11]     <- Грань D (Низ, индексы 9-17). Центр = [13]
      [12][13][14]
      [15][16][17]
 */
    public void print() {
        int[] array = getStickersCopy();
        System.out.printf("\t\t\t[%c] [%c] [%c]%n", color(array[0]), color(array[1]), color(array[2]));
        System.out.printf("\t\t\t[%c] [%c] [%c]%n", color(array[3]), color(array[4]), color(array[5]));
        System.out.printf("\t\t\t[%c] [%c] [%c]%n", color(array[6]), color(array[7]), color(array[8]));
        System.out.printf("[%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c]%n",
                color(array[18]), color(array[19]), color(array[20]), color(array[36]), color(array[37]), color(array[38]),
                color(array[27]), color(array[28]), color(array[29]), color(array[45]), color(array[46]), color(array[47]));
        System.out.printf("[%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c]%n",
                color(array[21]), color(array[22]), color(array[23]), color(array[39]), color(array[40]), color(array[41]),
                color(array[30]), color(array[31]), color(array[32]), color(array[48]), color(array[49]), color(array[50]));
        System.out.printf("[%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c] [%c]%n",
                color(array[24]), color(array[25]), color(array[26]), color(array[42]), color(array[43]), color(array[44]),
                color(array[33]), color(array[34]), color(array[35]), color(array[51]), color(array[52]), color(array[53]));
        System.out.printf("\t\t\t[%c] [%c] [%c]%n", color(array[9]), color(array[10]), color(array[11]));
        System.out.printf("\t\t\t[%c] [%c] [%c]%n", color(array[12]), color(array[13]), color(array[14]));
        System.out.printf("\t\t\t[%c] [%c] [%c]%n", color(array[15]), color(array[16]), color(array[17]));
    }
}
