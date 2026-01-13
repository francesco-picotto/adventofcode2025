package software.ulpgc.adventofcode2025.days.day12.strategy;

import software.ulpgc.adventofcode2025.days.day12.domain.Point;
import software.ulpgc.adventofcode2025.days.day12.domain.Shape;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of FittingStrategy using backtracking algorithm to solve the packing puzzle.
 *
 * This strategy attempts to place shapes one at a time, trying all possible positions and
 * orientations for each shape. If a placement leads to a dead end, it backtracks and tries
 * a different configuration. This exhaustive search guarantees finding a solution if one exists.
 *
 * The algorithm is particularly effective for smaller puzzle instances where the search space
 * is manageable, though it may be slow for large or complex puzzles.
 */
public class BacktrackingFittingStrategy implements FittingStrategy {

    /**
     * Determines if the given shapes can fit into the specified rectangular region
     * using a backtracking approach.
     *
     * @param width the width of the target region
     * @param height the height of the target region
     * @param variations a list of shape variation sets, one set per shape to be placed
     * @return true if all shapes can be successfully placed, false otherwise
     */
    @Override
    public boolean canFit(int width, int height, List<Set<Shape>> variations) {
        return solve(width, height, new HashSet<>(), variations, 0);
    }

    /**
     * Recursive backtracking solver that attempts to place shapes sequentially.
     *
     * This method tries to place the shape at the current index in all possible positions
     * and orientations. For each valid placement, it recursively attempts to place the
     * remaining shapes. If any path leads to successfully placing all shapes, it returns true.
     *
     * Algorithm steps:
     * 1. Base case: if all shapes are placed (index == variations.size()), return success
     * 2. For each variation of the current shape:
     *    a. Try placing it at every position in the grid
     *    b. If placement is valid, mark those cells as occupied
     *    c. Recursively attempt to place remaining shapes
     *    d. If successful, return true
     *    e. Otherwise, backtrack by unmarking the cells and try next position/variation
     * 3. If no valid placement found for current shape, return false to trigger backtracking
     *
     * @param w the region width
     * @param h the region height
     * @param occupied the set of currently occupied points in the region
     * @param variations the list of all shape variation sets
     * @param index the current shape index being placed
     * @return true if all remaining shapes (from index onward) can be placed, false otherwise
     */
    private boolean solve(int w, int h, Set<Point> occupied, List<Set<Shape>> variations, int index) {
        // Base case: all shapes have been successfully placed
        if (index == variations.size()) return true;

        // Try each variation of the current shape (already normalized)
        for (Shape variant : variations.get(index)) {
            // Try placing the variant at every possible position in the grid
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    // Check if this variant can be placed at position (x, y)
                    if (canPlace(variant, x, y, w, h, occupied)) {
                        // Place the shape and mark cells as occupied
                        Set<Point> placed = place(variant, x, y);
                        occupied.addAll(placed);

                        // Recursively try to place the next shape
                        if (solve(w, h, occupied, variations, index + 1)) {
                            return true; // Solution found!
                        }

                        // Backtrack: remove this placement and try another
                        occupied.removeAll(placed);
                    }
                }
            }
        }

        // No valid placement found for this shape - trigger backtracking
        return false;
    }

    /**
     * Checks if a shape can be placed at the specified position without conflicts.
     *
     * A placement is valid if:
     * 1. All translated points fit within the region bounds (0 to width-1, 0 to height-1)
     * 2. None of the translated points overlap with already occupied cells
     *
     * @param s the shape to place
     * @param dx the x-offset for placement (top-left corner)
     * @param dy the y-offset for placement (top-left corner)
     * @param w the region width
     * @param h the region height
     * @param occupied the set of currently occupied points
     * @return true if the shape can be validly placed at (dx, dy), false otherwise
     */
    private boolean canPlace(Shape s, int dx, int dy, int w, int h, Set<Point> occupied) {
        for (Point p : s.points()) {
            int tx = p.x() + dx;
            int ty = p.y() + dy;

            // Check bounds and collision with occupied cells
            if (tx >= w || ty >= h || occupied.contains(new Point(tx, ty))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Translates all points of a shape by the given offsets to create the placed configuration.
     * This produces the actual set of grid cells that will be occupied by placing the shape
     * at position (dx, dy).
     *
     * @param s the shape to place
     * @param dx the x-offset for placement
     * @param dy the y-offset for placement
     * @return a Set of Points representing the actual occupied cells after placement
     */
    private Set<Point> place(Shape s, int dx, int dy) {
        return s.points().stream()
                .map(p -> new Point(p.x() + dx, p.y() + dy))
                .collect(java.util.stream.Collectors.toSet());
    }
}