package software.ulpgc.adventofcode2025.days.day09.domain;

/**
 * Represents a rectangle in a 2D grid, defined by its minimum and maximum x and y coordinates.
 * This immutable record provides utilities for rectangle creation, area calculation, and center point determination.
 */
public record Rectangle(int xMin, int xMax, int yMin, int yMax) {

    /**
     * Creates a rectangle from two tiles by determining the bounding box that contains both points.
     * The resulting rectangle uses the minimum and maximum coordinates from both tiles.
     *
     * @param t1 the first tile
     * @param t2 the second tile
     * @return a new Rectangle that bounds both input tiles
     */
    public static Rectangle from(Tile t1, Tile t2) {
        return new Rectangle(
                Math.min(t1.x(), t2.x()),
                Math.max(t1.x(), t2.x()),
                Math.min(t1.y(), t2.y()),
                Math.max(t1.y(), t2.y())
        );
    }

    /**
     * Calculates the area of this rectangle.
     * The area includes all grid cells within the rectangle's boundaries (inclusive).
     * Uses long to prevent integer overflow for large rectangles.
     *
     * @return the area of the rectangle in grid cells
     */
    public long area() {
        return (long) (xMax - xMin + 1) * (yMax - yMin + 1);
    }

    /**
     * Calculates the x-coordinate of the rectangle's center point.
     * Returns a double value to represent the exact center, which may fall between grid cells.
     *
     * @return the x-coordinate of the center point
     */
    public double centerX() {
        return (xMin + xMax) / 2.0;
    }

    /**
     * Calculates the y-coordinate of the rectangle's center point.
     * Returns a double value to represent the exact center, which may fall between grid cells.
     *
     * @return the y-coordinate of the center point
     */
    public double centerY() {
        return (yMin + yMax) / 2.0;
    }
}