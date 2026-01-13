package software.ulpgc.adventofcode2025.days.day09.domain;

/**
 * Represents a single tile or coordinate point in a 2D grid.
 * This immutable record stores x and y coordinates and provides distance calculation utilities.
 */
public record Tile(int x, int y) {

    /**
     * Calculates the horizontal distance between this tile and another tile.
     * The distance is calculated as the absolute difference plus one, representing
     * the number of cells spanned (inclusive of both endpoints).
     *
     * @param other the tile to calculate distance to
     * @return the horizontal distance including both endpoint tiles
     */
    public int distanceX(Tile other) {
        return Math.abs(this.x - other.x) + 1;
    }

    /**
     * Calculates the vertical distance between this tile and another tile.
     * The distance is calculated as the absolute difference plus one, representing
     * the number of cells spanned (inclusive of both endpoints).
     *
     * @param other the tile to calculate distance to
     * @return the vertical distance including both endpoint tiles
     */
    public int distanceY(Tile other) {
        return Math.abs(this.y - other.y) + 1;
    }
}