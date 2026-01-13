package software.ulpgc.adventofcode2025.days.day05.analyzer;

import java.util.List;

/**
 * Strategy interface for analyzing inventory freshness data.
 *
 * Implementations of this interface define different approaches to analyzing
 * the relationship between fresh ingredient ranges and available ingredient IDs.
 * For example, one implementation might count fresh items in stock, while another
 * calculates total fresh capacity.
 */
public interface InventoryAnalyzer {
    /**
     * Analyzes inventory data to produce a numerical result.
     *
     * The specific meaning of the result depends on the implementation:
     * - StockFreshnessChecker returns the count of available IDs that are fresh
     * - TotalFreshCapacityEstimator returns the total capacity of fresh ranges
     *
     * @param freshRanges List of string representations of fresh ranges (e.g., "100-200")
     * @param availableIds List of string representations of available IDs (e.g., "150")
     * @return The analysis result as a long integer
     */
    long analyze(List<String> freshRanges, List<String> availableIds);
}