package software.ulpgc.adventofcode2025.days.day06.analyzer;

import software.ulpgc.adventofcode2025.days.day06.domain.CephalopodProblem;
import software.ulpgc.adventofcode2025.days.day06.domain.Operator;
import software.ulpgc.adventofcode2025.days.day06.util.WorksheetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Advanced analyzer that parses worksheet problems from right to left with vertical digit reading.
 *
 * This analyzer reads the worksheet in reverse (right-to-left) order and interprets
 * numbers vertically within each problem block. Each column represents a single digit,
 * and digits are read vertically from top to bottom (excluding the operator row) to
 * form multi-digit numbers. The numbers are then assembled right-to-left within each block.
 */
public class ReverseVerticalAnalyzer implements MathWorksheetAnalyzer {

    /**
     * Analyzes the worksheet by parsing problems right-to-left with vertical reading.
     *
     * Scans the worksheet from right to left, identifying problem blocks separated
     * by empty columns. Within each block, reads digits vertically and assembles
     * numbers in reverse order. Solves each problem and returns the sum.
     *
     * @param lines List of strings representing the visual worksheet layout
     * @return The sum of all problem results
     */
    @Override
    public long analyze(List<String> lines) {
        List<CephalopodProblem> problems = new ArrayList<>();
        int width = lines.get(0).length();
        int endCol = width;

        // Scan from right to left to identify column blocks
        for (int j = width - 1; j >= -1; j--) {
            // Use static method from WorksheetUtils instead of inheritance
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

    /**
     * Extracts a single problem from a column range using vertical digit reading.
     *
     * Within the specified column range, reads digits vertically to form numbers:
     * 1. Processes columns from right to left within the block
     * 2. For each column, reads vertically (excluding the operator row) to get a digit sequence
     * 3. Interprets each vertical sequence as a complete number
     * 4. Extracts the operator from the last row of the block
     *
     * Visual example (columns 2-5, showing one problem block):
     * Row 0: " 1 2"  → Column 3 has '1', Column 4 has '2'
     * Row 1: " 3 4"  → Column 3 has '3', Column 4 has '4'
     * Row 2: "  * "  → Operator row
     *
     * Processing right-to-left:
     * - Column 4: vertical read "24" → number 24
     * - Column 3: vertical read "13" → number 13
     * Result: CephalopodProblem([24, 13], MULTIPLY)
     *
     * This interpretation allows for a different way of encoding numbers where each
     * column represents a multi-digit number read vertically.
     *
     * @param lines The worksheet lines
     * @param start The starting column index (inclusive) of the problem block
     * @param end The ending column index (exclusive) of the problem block
     * @return A CephalopodProblem extracted with vertical digit reading
     */
    private CephalopodProblem extractVerticalProblem(List<String> lines, int start, int end) {
        List<Long> nums = new ArrayList<>();

        // Extract numbers from columns in the identified block
        for (int j = end - 1; j >= start; j--) {
            // Exclude the last row (which contains the operator) during number extraction
            String columnDigits = WorksheetUtils.getColumnPart(lines.subList(0, lines.size() - 1), j).trim();
            if (!columnDigits.isEmpty()) {
                nums.add(Long.parseLong(columnDigits));
            }
        }

        // The operator is found in the last row of the block
        char opChar = lines.get(lines.size() - 1).substring(start, end).trim().charAt(0);
        return new CephalopodProblem(nums, Operator.fromChar(opChar));
    }
}