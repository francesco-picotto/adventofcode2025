package software.ulpgc.adventofcode2025.days.day06.analyzer;

import java.util.List;

/**
 * Strategy interface for analyzing and solving math worksheet problems.
 *
 * Implementations of this interface define different approaches to parsing
 * visual representations of math problems arranged in columns. Different
 * analyzers may parse in different directions (left-to-right vs right-to-left)
 * or interpret the column structure differently (horizontal vs vertical reading).
 */
public interface MathWorksheetAnalyzer {
    /**
     * Analyzes a visual worksheet representation and calculates the sum of all problem results.
     *
     * Parses the visual layout to extract individual math problems, solves each
     * problem according to its operator, and returns the sum of all results.
     *
     * The visual format typically shows:
     * - Numbers arranged in rows
     * - An operator symbol in the bottom row
     * - Problems separated by empty columns
     *
     * @param lines List of strings representing the visual worksheet, where each string is a row
     * @return The sum of all problem results in the worksheet
     */
    long analyze(List<String> lines);
}