package software.ulpgc.adventofcode2025.days.day07.service;

import software.ulpgc.adventofcode2025.days.day07.analyzer.ManifoldAnalyzer;

import java.util.List;

/**
 * Service class that processes tachyon manifold grids using different analysis strategies.
 *
 * This processor follows the established pattern from previous days, using dependency
 * injection to receive an analyzer that defines how to simulate beam behavior through
 * the manifold. The processor delegates all simulation and calculation logic to the
 * configured analyzer.
 */
public class ManifoldProcessor {
    private final ManifoldAnalyzer analyzer;

    /**
     * Constructs a ManifoldProcessor with the specified analysis strategy.
     *
     * Uses dependency injection to receive the analyzer that will define how
     * beams are tracked through the manifold and what metrics are calculated
     * (e.g., split count vs. timeline count).
     *
     * @param analyzer The analyzer to use for processing the manifold
     */
    public ManifoldProcessor(ManifoldAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * Processes the tachyon manifold grid and returns the analysis result.
     *
     * Delegates the entire simulation and calculation process to the configured
     * analyzer, which tracks beam behavior through the manifold and computes
     * the appropriate metric.
     *
     * @param grid List of strings representing the manifold grid layout
     * @return The analysis result (split count or timeline count depending on analyzer)
     */
    public long solve(List<String> grid) {
        return analyzer.analyze(grid);
    }
}