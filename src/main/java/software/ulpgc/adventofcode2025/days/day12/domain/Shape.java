package software.ulpgc.adventofcode2025.days.day12.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a geometric shape composed of multiple points in 2D space.
 * This immutable record encapsulates a set of points that together form a puzzle piece
 * or polyomino, providing operations for transformations and normalization.
 *
 * Shapes can be rotated, flipped, and normalized to a canonical form starting at (0,0).
 * This is essential for puzzle-solving where pieces can be oriented in multiple ways.
 */
public record Shape(Set<Point> points) {

    /**
     * Normalizes the shape by translating it so its top-left corner is at origin (0,0).
     * This creates a canonical representation where the minimum x and y coordinates
     * among all points are shifted to zero.
     *
     * Normalization is crucial for comparing shapes and ensuring consistent placement,
     * as it removes positional variance while preserving the shape's structure.
     *
     * Example: Shape with points {(2,3), (3,3), (2,4)} becomes {(0,0), (1,0), (0,1)}
     *
     * @return a new Shape translated to have its top-left point at origin
     */
    public Shape normalize() {
        // Find the minimum x and y coordinates
        int minX = points.stream().mapToInt(Point::x).min().orElse(0);
        int minY = points.stream().mapToInt(Point::y).min().orElse(0);

        // Translate all points by the negative of these minimums
        return new Shape(points.stream()
                .map(p -> p.translate(-minX, -minY))
                .collect(Collectors.toSet()));
    }

    /**
     * Generates all unique variations of this shape through rotations and flips.
     * This method produces all possible orientations of the shape by applying
     * four 90-degree rotations and flipping after each rotation, then normalizing.
     *
     * The algorithm generates up to 8 variations (4 rotations × 2 flip states),
     * though symmetric shapes may produce fewer unique results. All variations
     * are normalized to ensure consistent comparison.
     *
     * This is essential for puzzle solving where a piece can be placed in any orientation.
     *
     * @return a Set of all unique normalized variations of this shape
     */
    public Set<Shape> getVariations() {
        Set<Shape> variants = new HashSet<>();
        Shape current = this;

        // Generate 4 rotations, each with its flipped version
        for (int i = 0; i < 4; i++) {
            current = current.rotate().normalize();
            variants.add(current);
            variants.add(current.flip().normalize());
        }
        return variants;
    }

    /**
     * Rotates all points in the shape 90 degrees clockwise.
     * Each point is rotated individually using the Point's rotation transformation.
     *
     * Note: The result may need normalization if used for placement, as rotation
     * can shift the shape's bounding box.
     *
     * @return a new Shape with all points rotated 90° clockwise
     */
    public Shape rotate() {
        return new Shape(points.stream()
                .map(Point::rotate)
                .collect(Collectors.toSet()));
    }

    /**
     * Flips all points in the shape horizontally.
     * Each point is flipped individually using the Point's flip transformation.
     *
     * Note: The result may need normalization if used for placement, as flipping
     * can shift the shape's bounding box.
     *
     * @return a new Shape with all points flipped horizontally
     */
    public Shape flip() {
        return new Shape(points.stream()
                .map(Point::flip)
                .collect(Collectors.toSet()));
    }
}