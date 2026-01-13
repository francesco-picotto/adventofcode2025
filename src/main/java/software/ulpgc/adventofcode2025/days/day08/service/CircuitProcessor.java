package software.ulpgc.adventofcode2025.days.day08.service;

import software.ulpgc.adventofcode2025.days.day08.domain.Day08Data;
import software.ulpgc.adventofcode2025.days.day08.analyzer.CircuitAnalyzer;

/**
 * Service class that processes circuit connectivity data using different analysis strategies.
 *
 * This processor follows the established pattern from previous days, using dependency
 * injection to receive an analyzer that defines how to evaluate circuit connectivity.
 * The processor delegates all connection analysis and component tracking logic to the
 * configured analyzer.
 */
public class CircuitProcessor {
    private final CircuitAnalyzer analyzer;

    /**
     * Constructs a CircuitProcessor with the specified analysis strategy.
     *
     * Uses dependency injection to receive the analyzer that will define how
     * connections are processed and what metrics are calculated (e.g., component
     * sizes vs. critical connection identification).
     *
     * @param analyzer The analyzer to use for processing circuit connectivity
     */
    public CircuitProcessor(CircuitAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * Processes the circuit data and returns the analysis result.
     *
     * Extracts junction boxes and connections from the Day08Data object and
     * delegates to the configured analyzer for processing. The result depends
     * on the analyzer implementation.
     *
     * @param data The Day08Data containing junction boxes and their possible connections
     * @return The analysis result (e.g., product of component sizes or connection metric)
     */
    public long solve(Day08Data data) {
        return analyzer.analyze(data.boxes(), data.connections());
    }
}