package ru.nsu.gorlova;


import java.util.List;

/**
 * Отчёт по анализатору.
 *
 * @param solved {@code true} кубик собран, иначе {@code false}
 * @param totalMoves количество ходов
 * @param stages на каком этапе остановились
 * @param errors список ошибок
 */
public record Report(
        boolean solved,
        int totalMoves,
        Stages stages,
        List<AnalysisError> errors) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Заголовок и общая информация
        sb.append("========================================\n");
        sb.append("       ASSEMBLY ANALYSIS REPORT\n");
        sb.append("========================================\n");
        sb.append("Cube solved: ").append(solved() ? "Yes" : "No").append("\n");
        sb.append("Total moves:  ").append(totalMoves()).append("\n");

        // Блок с этапами сборки
        sb.append("\n[ ASSEMBLY STAGES ]\n");
        if (stages() != null) {
            appendStage(sb, "Cross", stages().cross());
            appendStage(sb, "F2L", stages().f2l());
            appendStage(sb, "OLL", stages().oll());
            appendStage(sb, "PLL", stages().pll());
        } else {
            sb.append("Information about the stages is unavailable.\n");
        }

        // Блок с ошибками
        sb.append("\n[ Errors ]\n");
        if (errors() == null || errors().isEmpty()) {
            sb.append("No errors found.\n");
        } else {
            for (int i = 0; i < errors().size(); i++) {
                AnalysisError error = errors().get(i);
                sb.append(String.format("%d. Stage: %s (Moves %d-%d)\n",
                        i + 1, error.stage(), error.moveStart(), error.moveEnd()));
                sb.append(String.format("   Completed: %s\n", error.actualMoves()));
                sb.append(String.format("   Recommendation: %s\n", error.suggestion()));
            }
        }

        sb.append("========================================\n");
        return sb.toString();
    }

    /**
     * Вспомогательный метод для красивого форматирования информации об этапе.
     */
    private void appendStage(StringBuilder sb, String name, StageInfo info) {
        if (info == null || !info.exists()) {
            sb.append(String.format("%-6s: the stage was not completed\n", name));
            return;
        }

        sb.append(String.format("%-6s: moves %3d -%3d (total %2d) | Status: %s\n",
                name,
                info.startMove(),
                info.endMove(),
                info.moveCount(),
                info.solved() ? "ASSEMBLED" : "NOT ASSEMBLED"));
    }


    /**
     * Этапы остановки.
     *
     * @param cross крест.
     * @param f2l первая и вторая грань
     * @param oll сторона последней грани
     * @param pll ребра последней грани
     */
    public record Stages(
            StageInfo cross,
            StageInfo f2l,
            StageInfo oll,
            StageInfo pll
    ) {}

    /**
     * Информация об этапе.
     *
     * @param startMove индекс начального хода
     * @param endMove индекс конечного хода
     * @param moveCount количество ходов
     * @param solved {@code true} этап собран, иначе этап не собран
     */
    public record StageInfo(
            int startMove,
            int endMove,
            int moveCount,
            boolean solved) {
        public boolean exists() {
            return moveCount > 0;
        }
        public int getEndMoves() {
            return endMove;
        }
    }

    /**
     * Анализ ошибки.
     *
     * @param stage этап ошибки
     * @param moveStart начало
     * @param moveEnd конец
     * @param actualMoves что сделал пользователь
     * @param suggestion какое правило CFOP можно было использовать
     */
    public record AnalysisError(
            StageName stage,
            int moveStart,
            int moveEnd,
            String actualMoves,
            String suggestion
    ) {
        public int getMoveEnd() {
            return moveEnd;
        }
        public String getActualMoves() {
            return actualMoves;
        }
        public String getSuggestion() {
            return suggestion;
        }
    }

    public enum StageName {
        F2L, OLL, PLL
    }
}