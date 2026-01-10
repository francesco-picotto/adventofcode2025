package software.ulpgc.adventofcode2025.day06;
import java.util.*;

public class StandardColumnAnalyzer implements MathWorksheetAnalyzer {
    @Override
    public long analyze(List<String> lines) {
        List<CephalopodProblem> problems = parseProblems(lines);
        return problems.stream().mapToLong(CephalopodProblem::solve).sum();
    }

    private List<CephalopodProblem> parseProblems(List<String> lines) {
        List<CephalopodProblem> problems = new ArrayList<>();
        int width = lines.get(0).length();
        int startCol = 0;

        for (int j = 0; j <= width; j++) {
            if (j == width || isColumnEmpty(lines, j)) {
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
        char op = ' ';
        for (int i = 0; i < lines.size(); i++) {
            String part = lines.get(i).substring(start, Math.min(end, lines.get(i).length())).trim();
            if (part.isEmpty()) continue;

            if (i == lines.size() - 1) op = part.charAt(0);
            else nums.add(Long.parseLong(part));
        }
        return new CephalopodProblem(nums, op);
    }

    private boolean isColumnEmpty(List<String> lines, int col) {
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') return false;
        }
        return true;
    }
}


