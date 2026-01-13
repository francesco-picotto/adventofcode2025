package software.ulpgc.adventofcode2025.days.day06.service;

import software.ulpgc.adventofcode2025.days.day06.analyzer.MathWorksheetAnalyzer;

import java.util.List;

/**
 * Service class that processes math worksheet problems using different analysis strategies.
 *
 * This processor follows the established pattern from previous days, using dependency
 * injection to receive an analyzer that defines how to parse and evaluate the visual
 * representation of math problems in the worksheet. The processor delegates all parsing
 * and calculation logic to the configured analyzer.
 */
public class MathWorksheetProcessor {
    private final MathWorksheetAnalyzer analyzer;

    /**
     * Constructs a MathWorksheetProcessor with the specified analysis strategy.
     *
     * Uses dependency injection to receive the analyzer that will define how
     * the worksheet is parsed (e.g., left-to-right or right-to-left) and how
     * problems are extracted from the visual column format.
     *
     * @param analyzer The analyzer to use for parsing and evaluating the worksheet
     */
    public MathWorksheetProcessor(MathWorksheetAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * Processes the math worksheet and returns the sum of all problem results.
     *
     * Delegates the entire parsing and calculation process to the configured
     * analyzer, which extracts individual problems from the visual representation,
     * solves each one, and sums the results.
     *
     * @param input List of strings representing the visual worksheet layout
     * @return The sum of all problem results in the worksheet
     */
    public long solve(List<String> input) {
        return analyzer.analyze(input);
    }
}