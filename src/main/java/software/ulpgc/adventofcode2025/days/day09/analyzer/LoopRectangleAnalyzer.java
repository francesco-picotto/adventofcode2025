package software.ulpgc.adventofcode2025.days.day09.analyzer;

import software.ulpgc.adventofcode2025.days.day09.domain.Rectangle;
import software.ulpgc.adventofcode2025.days.day09.domain.Tile;
import software.ulpgc.adventofcode2025.days.day09.util.GeometryUtils;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Analyzer implementation that finds the maximum valid rectangle area within a polygon.
 * A rectangle is considered valid if its center is inside the polygon formed by the tiles
 * and none of the polygon edges intersect the rectangle's interior.
 *
 * This implementation uses parallel processing for improved performance on large datasets.
 */
public class LoopRectangleAnalyzer implements GridAnalyzer {

    /**
     * Analyzes all tiles to find the maximum valid rectangle area within the polygon.
     * Uses parallel processing to improve performance for large datasets.
     *
     * Time complexity: O(n³) where n is the number of tiles, as each pair is validated
     * against all polygon edges.
     *
     * @param tiles the list of tiles forming the polygon and potential rectangle corners
     * @return the maximum area of a valid rectangle, or 0 if no valid rectangles exist
     *         or if the input is invalid (null or fewer than 2 tiles)
     */
    @Override
    public long analyze(List<Tile> tiles) {
        if (tiles == null || tiles.size() < 2) return 0L;

        int n = tiles.size();
        // Use parallel streams to speed up computation on large datasets (O(N³) complexity)
        return IntStream.range(0, tiles.size())
                .parallel()
                .mapToLong(i -> calculateMaxForTile(i, tiles))
                .max()
                .orElse(0L);
    }

    /**
     * Calculates the maximum valid rectangle area for rectangles starting with a specific tile.
     * This method pairs the given tile with all subsequent tiles and finds the largest
     * valid rectangle among them.
     *
     * @param i the index of the first tile in the pair
     * @param tiles the complete list of tiles
     * @return the maximum valid rectangle area for this starting tile
     */
    private long calculateMaxForTile(int i, List<Tile> tiles) {
        long localMax = 0;
        Tile t1 = tiles.get(i);

        // Pair with all subsequent tiles to avoid duplicate pairs
        for (int j = i + 1; j < tiles.size(); j++) {
            Rectangle rect = Rectangle.from(t1, tiles.get(j));
            long area = rect.area();

            // Update local maximum if this rectangle is larger and valid
            if (area > localMax && isValid(rect, tiles)) {
                localMax = area;
            }
        }
        return localMax;
    }

    /**
     * Validates whether a rectangle is properly contained within the polygon.
     * A rectangle is valid if:
     * 1. Its center point is inside the polygon
     * 2. None of the polygon's edges intersect the rectangle's interior
     *
     * @param rect the rectangle to validate
     * @param polygon the list of tiles forming the polygon boundary
     * @return true if the rectangle is valid, false otherwise
     */
    private boolean isValid(Rectangle rect, List<Tile> polygon) {
        // First check if the rectangle's center is inside the polygon
        if (!GeometryUtils.isPointInside(rect.centerX(), rect.centerY(), polygon)) {
            return false;
        }

        // Check if any polygon edge intersects the rectangle's interior
        for (int i = 0; i < polygon.size(); i++) {
            if (isEdgeIntersectingRect(polygon.get(i), polygon.get((i + 1) % polygon.size()), rect)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a polygon edge intersects with the interior of a rectangle.
     * The edge must pass through the rectangle's interior (not just touch its boundary).
     * Handles both vertical and horizontal edges.
     *
     * @param a the first endpoint of the edge
     * @param b the second endpoint of the edge
     * @param rect the rectangle to check against
     * @return true if the edge intersects the rectangle's interior, false otherwise
     */
    private boolean isEdgeIntersectingRect(Tile a, Tile b, Rectangle rect) {
        if (a.x() == b.x()) {
            // Vertical edge: check if it passes through the rectangle horizontally
            // and overlaps vertically
            return a.x() > rect.xMin() && a.x() < rect.xMax() &&
                    Math.max(a.y(), b.y()) > rect.yMin() &&
                    Math.min(a.y(), b.y()) < rect.yMax();
        } else {
            // Horizontal edge: check if it passes through the rectangle vertically
            // and overlaps horizontally
            return a.y() > rect.yMin() && a.y() < rect.yMax() &&
                    Math.max(a.x(), b.x()) > rect.xMin() &&
                    Math.min(a.x(), b.x()) < rect.xMax();
        }
    }
}