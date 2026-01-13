package software.ulpgc.adventofcode2025.days.day09.analyzer;

import software.ulpgc.adventofcode2025.days.day09.domain.Rectangle;
import software.ulpgc.adventofcode2025.days.day09.domain.Tile;

import java.util.*;

/**
 * Analyzer implementation that finds the maximum rectangle area that can be formed
 * by any pair of tiles in the grid. This implementation considers all possible
 * rectangle combinations without validation constraints.
 */
public class MaxRectangleAnalyzer implements GridAnalyzer {

    /**
     * Analyzes all tiles to find the maximum rectangle area.
     * This method examines every possible pair of tiles and calculates the area
     * of the rectangle formed by each pair, returning the maximum area found.
     *
     * Time complexity: O(n²) where n is the number of tiles.
     *
     * @param tiles the list of tiles to analyze
     * @return the maximum rectangle area that can be formed by any pair of tiles
     */
    @Override
    public long analyze(List<Tile> tiles) {
        long maxArea = 0;

        // Iterate through all unique pairs of tiles
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                // Create a rectangle from the current pair of tiles
                Rectangle rect = Rectangle.from(tiles.get(i), tiles.get(j));
                // Update the maximum area if this rectangle is larger
                maxArea = Math.max(maxArea, rect.area());
            }
        }
        return maxArea;
    }
}