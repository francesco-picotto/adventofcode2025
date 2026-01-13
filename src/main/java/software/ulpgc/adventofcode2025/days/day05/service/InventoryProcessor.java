package software.ulpgc.adventofcode2025.days.day05.service;

import software.ulpgc.adventofcode2025.days.day05.analyzer.InventoryAnalyzer;
import software.ulpgc.adventofcode2025.days.day05.domain.Day05Data;

import java.util.List;

/**
 * Service class that processes inventory data using different analysis strategies.
 *
 * This processor follows the same pattern as PasswordProcessor (Day 01) and
 * IdProcessor (Day 02), using dependency injection to receive an analysis strategy
 * that defines how to evaluate the inventory data. The processor delegates the
 * actual analysis logic to the configured analyzer while providing validation
 * and multiple convenience methods for data input.
 */
public class InventoryProcessor {
    private final InventoryAnalyzer analyzer;

    /**
     * Constructs an InventoryProcessor with the specified analysis strategy.
     *
     * Uses dependency injection to receive the analyzer that will define how
     * the inventory data is processed and what results are calculated.
     *
     * @param analyzer The analyzer to use for processing inventory data
     * @throws IllegalArgumentException if analyzer is null
     */
    public InventoryProcessor(InventoryAnalyzer analyzer) {
        if (analyzer == null) {
            throw new IllegalArgumentException("Analyzer cannot be null");
        }
        this.analyzer = analyzer;
    }

    /**
     * Processes the inventory data using the configured analyzer.
     *
     * Extracts the ranges and IDs from the Day05Data object and delegates
     * to the analyzer for processing. The result depends on the analyzer
     * implementation (e.g., count of fresh items or total capacity).
     *
     * @param data The Day05Data containing ranges and ids
     * @return The analysis result (count or total capacity depending on analyzer)
     * @throws IllegalArgumentException if data is null
     */
    public long analyze(Day05Data data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return analyzer.analyze(data.ranges(), data.ids());
    }

    /**
     * Processes inventory data from separate lists.
     *
     * Convenience method that accepts ranges and IDs as separate parameters
     * rather than wrapped in a Day05Data object. Validates inputs and delegates
     * to the configured analyzer.
     *
     * @param freshRanges List of fresh ingredient ranges in string format (e.g., "100-200")
     * @param availableIds List of available ingredient IDs in string format (e.g., "150")
     * @return The analysis result based on the configured analyzer
     * @throws IllegalArgumentException if either parameter is null
     */
    public long analyze(List<String> freshRanges, List<String> availableIds) {
        if (freshRanges == null || availableIds == null) {
            throw new IllegalArgumentException("Ranges and IDs cannot be null");
        }
        return analyzer.analyze(freshRanges, availableIds);
    }

    /**
     * Gets the analyzer being used by this processor.
     *
     * Provides access to the configured analyzer, which may be useful for
     * testing or debugging purposes.
     *
     * @return The InventoryAnalyzer instance used by this processor
     */
    public InventoryAnalyzer getAnalyzer() {
        return analyzer;
    }
}