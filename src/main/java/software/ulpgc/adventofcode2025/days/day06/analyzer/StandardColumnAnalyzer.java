package software.ulpgc.adventofcode2025.days.day06.analyzer;

import software.ulpgc.adventofcode2025.days.day06.domain.CephalopodProblem;
import software.ulpgc.adventofcode2025.days.day06.domain.Operator;
import software.ulpgc.adventofcode2025.days.day06.util.WorksheetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard analyzer that parses worksheet problems from left to right.
 *
 * This analyzer reads the worksheet in the natural left-to-right order,
 * identifying problems separated by empty columns. Within each problem block,
 * it reads numbers horizontally (one per row) with the operator symbol in
 * the last row. This represents the standard way of reading a worksheet.
 */
public class StandardColumnAnalyzer implements MathWorksheetAnalyzer {

    /**
     * Analyzes the worksheet by parsing problems left-to-right and summing their results.
     *
     * Parses all problems from the worksheet in standard left-to-right order,
     * solves each problem according to its operator, and returns the sum of
     * all results.
     *
     * @param lines List of strings representing the visual worksheet layout
     * @return The sum of all problem results
     */
    @Override
    public long analyze(List<String> lines) {
        List<CephalopodProblem> problems = parseProblems(lines);
        return problems.stream()
                .mapToLong(CephalopodProblem::solve)
                .sum();
    }

    /**
     * Parses all problems from the worksheet by scanning left to right.
     *
     * Scans through the worksheet columns from left to right, using empty
     * columns as separators to identify individual problem blocks. When an
     * empty column or the end of the worksheet is reached, extracts the
     * accumulated problem block.
     *
     * Algorithm:
     * 1. Start at column 0
     * 2. Scan right until finding an empty column or reaching the end
     * 3. Extract the problem from the accumulated columns
     * 4. Skip the empty column and repeat from the next non-empty column
     *
     * @param lines The worksheet lines to parse
     * @return A list of all extracted problems in left-to-right order
     */
    private List<CephalopodProblem> parseProblems(List<String> lines) {
        List<CephalopodProblem> problems = new ArrayList<>();
        int width = lines.get(0).length();
        int startCol = 0;

        for (int j = 0; j <= width; j++) {
            // Use static utility instead of inheritance
            if (j == width || WorksheetUtils.isColumnEmpty(lines, j)) {
                if (j > startCol) {
                    problems.add(extractProblem(lines, startCol, j));
                }
                startCol = j + 1;
            }
        }
        return problems;
    }

    /**
     * Extracts a single problem from a column range of the worksheet.
     *
     * Reads through the specified column range and extracts:
     * - Numbers from all rows except the last (one number per row)
     * - The operator character from the last row
     *
     * The visual format within a problem block:
     * Row 0: first number
     * Row 1: second number
     * ...
     * Last row: operator symbol ('+' or '*')
     *
     * Example problem block (columns 0-2):
     * "123"  → number 123
     * "456"  → number 456
     * " + "  → ADD operator
     * Result: CephalopodProblem([123, 456], ADD)
     *
     * @param lines The worksheet lines
     * @param start The starting column index (inclusive) of the problem block
     * @param end The ending column index (exclusive) of the problem block
     * @return A CephalopodProblem extracted from the specified column range
     */
    private CephalopodProblem extractProblem(List<String> lines, int start, int end) {
        List<Long> nums = new ArrayList<>();
        char opChar = ' ';
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String part = line.substring(start, Math.min(end, line.length())).trim();

            if (part.isEmpty()) continue;

            // The last row contains the operator, the others contain numbers
            if (i == lines.size() - 1) {
                opChar = part.charAt(0);
            } else {
                nums.add(Long.parseLong(part));
            }
        }
        return new CephalopodProblem(nums, Operator.fromChar(opChar));
    }
}