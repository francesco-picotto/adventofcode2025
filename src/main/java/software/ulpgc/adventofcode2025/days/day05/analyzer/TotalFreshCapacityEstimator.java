package software.ulpgc.adventofcode2025.days.day05.analyzer;
import software.ulpgc.adventofcode2025.days.day05.domain.IngredientRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Analyzer that calculates the total capacity of all fresh ingredient ranges.
 *
 * This implementation merges overlapping or adjacent ranges before calculating
 * the total capacity. By merging ranges first, it avoids double-counting IDs
 * that appear in multiple overlapping ranges. This provides an accurate measure
 * of the total number of unique ingredient IDs that are considered fresh.
 */
public class TotalFreshCapacityEstimator implements InventoryAnalyzer {
    /**
     * Analyzes fresh ranges to calculate the total capacity of unique fresh IDs.
     *
     * The analysis process:
     * 1. Parses all fresh range strings into IngredientRange objects
     * 2. Sorts the ranges by their start position
     * 3. Merges overlapping or adjacent ranges into consolidated ranges
     * 4. Calculates the size of each merged range and sums them
     *
     * Merging is necessary to avoid counting the same ID multiple times when
     * ranges overlap. For example, ranges [100-200] and [150-250] overlap,
     * so they should be merged into [100-250] with a size of 151, not counted
     * as 101 + 101 = 202.
     *
     * Example:
     * - Fresh ranges: ["100-200", "150-250", "300-400"]
     * - After merging: ["100-250", "300-400"]
     * - Result: 151 + 101 = 252
     *
     * @param freshRanges List of string representations of fresh ranges (e.g., "100-200")
     * @param availableIds List of available IDs (not used by this analyzer)
     * @return The total capacity of all merged fresh ranges
     */
    @Override
    public long analyze(List<String> freshRanges, List<String> availableIds) {

        List<IngredientRange> sortedRanges = freshRanges.stream()
                .map(IngredientRange::parse)
                .sorted()
                .toList();

        return mergeRanges(sortedRanges).stream()
                .mapToLong(IngredientRange::size)
                .sum();
    }

    /**
     * Merges overlapping or adjacent ranges into a minimal set of non-overlapping ranges.
     *
     * This algorithm uses a greedy approach:
     * 1. Starts with the first range as the current range
     * 2. For each subsequent range:
     *    - If it overlaps or is adjacent to the current range (next.start <= current.end + 1),
     *      extend the current range to include it
     *    - Otherwise, add the current range to the result and start a new current range
     * 3. Add the final current range to the result
     *
     * Two ranges are considered mergeable if the next range starts at or before
     * the current range's end + 1. This includes:
     * - Overlapping ranges: [100-200] and [150-250]
     * - Adjacent ranges: [100-200] and [201-300]
     * - Contained ranges: [100-300] and [150-200]
     *
     * Example:
     * - Input: [100-200], [150-250], [300-400], [350-500]
     * - Output: [100-250], [300-500]
     *
     * @param ranges A list of IngredientRange objects sorted by start position
     * @return A list of merged ranges with no overlaps or adjacencies
     */
    private List<IngredientRange> mergeRanges(List<IngredientRange> ranges) {
        if(ranges.isEmpty()) return Collections.emptyList();

        List<IngredientRange> merged = new ArrayList<>();
        IngredientRange current = ranges.get(0);

        for(int i = 1; i < ranges.size(); i++){
            IngredientRange next = ranges.get(i);
            if(next.start() <= current.end() + 1){
                current = new IngredientRange(current.start(), Math.max(current.end(), next.end()));
            }
            else{
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        return merged;
    }

}