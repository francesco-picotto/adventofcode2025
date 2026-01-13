package software.ulpgc.adventofcode2025.days.day06;

import java.util.ArrayList;
import java.util.List;

public class StandardColumnAnalyzer implements MathWorksheetAnalyzer {

    @Override
    public long analyze(List<String> lines) {
        List<CephalopodProblem> problems = parseProblems(lines);
        return problems.stream()
                .mapToLong(CephalopodProblem::solve)
                .sum();
    }

    private List<CephalopodProblem> parseProblems(List<String> lines) {
        List<CephalopodProblem> problems = new ArrayList<>();
        int width = lines.get(0).length();
        int startCol = 0;

        for (int j = 0; j <= width; j++) {
            // Usiamo la utility statica invece di super
            if (j == width || WorksheetUtils.isColumnEmpty(lines, j)) {
                if (j > startCol) {
                    problems.add(extractProblem(lines, startCol, j));
                }
                startCol = j + 1;
            }
        }
        return problems;
    }

    private CephalopodProblem extractProblem(List<String> lines, int start, int end) {
        List<Long> nums = new ArrayList<>();
        char opChar = ' ';
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String part = line.substring(start, Math.min(end, line.length())).trim();

            if (part.isEmpty()) continue;

            // L'ultima riga contiene l'operatore, le altre i numeri
            if (i == lines.size() - 1) {
                opChar = part.charAt(0);
            } else {
                nums.add(Long.parseLong(part));
            }
        }
        return new CephalopodProblem(nums, Operator.fromChar(opChar));
    }
}

