package software.ulpgc.adventofcode2025.days.day12.domain;

/**
 * Represents a point in 2D space with integer coordinates.
 * This immutable record provides geometric transformations (rotation, flipping, translation)
 * relative to a 3x3 grid coordinate system.
 *
 * The transformations are designed for puzzle piece manipulation where shapes are
 * defined within a normalized 3x3 coordinate space (0-2 for both x and y).
 */
public record Point(int x, int y) {

    /**
     * Rotates the point 90 degrees clockwise around the center of a 3x3 grid.
     * The transformation formula for clockwise rotation in a 3x3 space is:
     * (x, y) → (2-y, x)
     *
     * This maps:
     * - Top-left (0,0) → Top-right (2,0)
     * - Top-right (2,0) → Bottom-right (2,2)
     * - Bottom-right (2,2) → Bottom-left (0,2)
     * - Bottom-left (0,2) → Top-left (0,0)
     *
     * @return a new Point representing this point rotated 90° clockwise
     */
    public Point rotate() {
        return new Point(2 - y, x);
    }

    /**
     * Flips the point horizontally across the vertical center axis of a 3x3 grid.
     * The transformation formula for horizontal flip in a 3x3 space is:
     * (x, y) → (2-x, y)
     *
     * This creates a mirror image where:
     * - Left side points move to right side
     * - Right side points move to left side
     * - Center column remains unchanged
     *
     * @return a new Point representing this point flipped horizontally
     */
    public Point flip() {
        return new Point(2 - x, y);
    }

    /**
     * Translates (moves) the point by specified offsets in x and y directions.
     * This is used to reposition shapes when placing them on a game board.
     *
     * @param dx the offset to add to the x-coordinate
     * @param dy the offset to add to the y-coordinate
     * @return a new Point at the translated position
     */
    public Point translate(int dx, int dy) {
        return new Point(x + dx, y + dy);
    }
}