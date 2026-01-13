package software.ulpgc.adventofcode2025.days.day06.util;

import java.util.List;

/**
 * Utility class providing helper methods for parsing worksheet column structures.
 *
 * This class contains static utility methods for analyzing and extracting data
 * from visual worksheet representations where problems are arranged in columns.
 * These utilities are shared by different analyzer implementations to avoid
 * code duplication.
 */
public class WorksheetUtils {
    /**
     * Checks if a specific column is empty across all lines.
     *
     * A column is considered empty if, for every line:
     * - The line is shorter than the column index (doesn't reach that column), OR
     * - The character at that column position is a space
     *
     * Empty columns typically serve as separators between different problems
     * in the worksheet layout.
     *
     * Example:
     * Lines: ["12 34", "56 78"]
     * Column 2 (index 2) is empty because both lines have a space there.
     *
     * @param lines The list of lines representing the worksheet
     * @param col The column index to check (0-based)
     * @return true if the column is empty across all lines, false otherwise
     */
    public static boolean isColumnEmpty(List<String> lines, int col) {
        return lines.stream()
                .allMatch(line -> col >= line.length() || line.charAt(col) == ' ');
    }

    /**
     * Extracts all non-space characters from a specific column across all lines.
     *
     * Scans through all lines and collects the characters at the specified column
     * position, skipping spaces and lines that don't extend to that column.
     * This is useful for reading vertical numbers where digits are stacked in
     * a single column.
     *
     * Example:
     * Lines: ["1 2", "2 3", "3 4"]
     * getColumnPart(lines, 0) → "123" (vertical reading of first column)
     * getColumnPart(lines, 2) → "234" (vertical reading of third column)
     *
     * @param lines The list of lines representing the worksheet
     * @param col The column index to extract (0-based)
     * @return A string containing all non-space characters from that column
     */
    public static String getColumnPart(List<String> lines, int col) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') {
                sb.append(line.charAt(col));
            }
        }
        return sb.toString();
    }
}