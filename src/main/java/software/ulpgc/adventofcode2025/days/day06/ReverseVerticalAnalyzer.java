package software.ulpgc.adventofcode2025.days.day06;

import java.util.ArrayList;
import java.util.List;

public class ReverseVerticalAnalyzer implements MathWorksheetAnalyzer {

    @Override
    public long analyze(List<String> lines) {
        List<CephalopodProblem> problems = new ArrayList<>();
        int width = lines.get(0).length();
        int endCol = width;

        // Scansione da destra verso sinistra per identificare i blocchi di colonne
        for (int j = width - 1; j >= -1; j--) {
            // Utilizzo del metodo statico di WorksheetUtils invece dell'ereditarietà
            if (j == -1 || WorksheetUtils.isColumnEmpty(lines, j)) {
                if (endCol > j + 1) {
                    problems.add(extractVerticalProblem(lines, j + 1, endCol));
                }
                endCol = j;
            }
        }
        return problems.stream()
                .mapToLong(CephalopodProblem::solve)
                .sum();
    }

    private CephalopodProblem extractVerticalProblem(List<String> lines, int start, int end) {
        List<Long> nums = new ArrayList<>();

        // Estrazione dei numeri dalle colonne nel blocco identificato
        for (int j = end - 1; j >= start; j--) {
            // Escludiamo l'ultima riga (che contiene l'operatore) durante l'estrazione dei numeri
            String columnDigits = WorksheetUtils.getColumnPart(lines.subList(0, lines.size() - 1), j).trim();
            if (!columnDigits.isEmpty()) {
                nums.add(Long.parseLong(columnDigits));
            }
        }

        // L'operatore si trova nell'ultima riga del blocco
        char opChar = lines.get(lines.size() - 1).substring(start, end).trim().charAt(0);
        return new CephalopodProblem(nums, Operator.fromChar(opChar));
    }
}