package software.ulpgc.adventofcode2025.days.day05.analyzer;
import software.ulpgc.adventofcode2025.days.day05.domain.IngredientRange;

import java.util.List;

/**
 * Analyzer that counts how many available ingredient IDs fall within fresh ranges.
 *
 * This implementation checks each available ID to determine if it falls within
 * any of the fresh ingredient ranges. IDs that are within at least one fresh
 * range are counted as fresh stock. This provides a measure of how much of the
 * available inventory is still fresh.
 */
public class StockFreshnessChecker implements InventoryAnalyzer {
    /**
     * Analyzes the inventory to count how many available IDs are fresh.
     *
     * The analysis process:
     * 1. Parses all fresh range strings into IngredientRange objects
     * 2. For each available ID, checks if it falls within any fresh range
     * 3. Counts the total number of IDs that are within at least one fresh range
     *
     * Example:
     * - Fresh ranges: ["100-200", "300-400"]
     * - Available IDs: ["150", "250", "350"]
     * - Result: 2 (IDs 150 and 350 are fresh, 250 is not)
     *
     * @param freshRanges List of string representations of fresh ranges (e.g., "100-200")
     * @param availableIds List of string representations of available IDs (e.g., "150")
     * @return The count of available IDs that fall within at least one fresh range
     */
    @Override
    public long analyze(List<String> freshRanges, List<String> availableIds) {

        List<IngredientRange> ranges = freshRanges.stream()
                .map(IngredientRange::parse)
                .toList();


        return availableIds.stream()
                .mapToLong(Long::parseLong)
                .filter(id->ranges.stream().anyMatch(r->r.contains(id)))
                .count();
    }

}