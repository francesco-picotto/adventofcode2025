package software.ulpgc.adventofcode2025.days.day07.domain;
import java.util.*;

/**
 * Represents a tachyon manifold grid where beams travel and interact with splitters.
 *
 * This domain object encapsulates a 2D grid structure that contains a starting position
 * marked with 'S' and splitter positions marked with '^'. It provides safe access to
 * grid cells and utilities for identifying splitters and determining the beam's starting
 * column. The manifold validates its structure upon construction to ensure proper setup.
 */
public class TachyonManifold{
    /**
     * Character symbol representing a beam splitter in the grid.
     */
    public static final char SPLITTER = '^';

    /**
     * Character symbol representing the starting position of the beam.
     */
    public static final char START = 'S';

    private final List<String> grid;
    private final int startColumn;

    /**
     * Constructs a TachyonManifold from a grid representation.
     *
     * Validates that the grid is not null or empty, then locates the starting
     * column marked with 'S' in the first row. The starting position determines
     * where the initial beam enters the manifold.
     *
     * @param grid List of strings representing the manifold, where each string is a row
     * @throws IllegalArgumentException if the grid is null or empty
     * @throws IllegalStateException if the 'S' starting marker is not found in the first row
     */
    public TachyonManifold(List<String> grid){
        if (grid == null || grid.isEmpty()) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }
        this.grid = grid;
        this.startColumn = grid.get(0).indexOf(START);
        if (this.startColumn == -1) {
            throw new IllegalStateException("'S' starting column is not found");
        }
    }

    /**
     * Retrieves the character at the specified position in the grid.
     *
     * Provides safe access to grid cells by returning a space character for
     * positions outside the grid boundaries instead of throwing an exception.
     * This simplifies boundary checking in beam simulation logic.
     *
     * @param row The row index (0-based)
     * @param col The column index (0-based)
     * @return The character at position (row, col), or ' ' if the position is out of bounds
     */
    public char getAt(int row, int col) {
        if (isOutOfBounds(row, col)) return ' ';
        return grid.get(row).charAt(col);
    }

    /**
     * Checks if the specified position contains a beam splitter.
     *
     * A splitter causes a beam traveling through it to split into two beams:
     * one going left and one going right in the next row.
     *
     * @param row The row index to check
     * @param col The column index to check
     * @return true if the position contains a splitter ('^'), false otherwise
     */
    public boolean isSplitter(int row, int col) {
        return getAt(row, col) == SPLITTER;
    }

    /**
     * Validates whether the given position is within the grid boundaries.
     *
     * @param row The row index to check
     * @param col The column index to check
     * @return true if the position is outside the grid, false if it's valid
     */
    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= grid.size() || col < 0 || col >= grid.get(0).length();
    }

    /**
     * Returns the column index where the beam starts.
     *
     * This is determined by finding the 'S' marker in the first row during
     * construction. Beams enter the manifold at this column.
     *
     * @return The starting column index (0-based)
     */
    public int getStartColumn(){return startColumn;}

    /**
     * Returns the number of rows in the manifold.
     *
     * @return The height of the grid
     */
    public int getHeight(){ return grid.size(); }

    /**
     * Returns the number of columns in the manifold.
     *
     * @return The width of the grid
     */
    public int getWidth(){ return grid.get(0).length(); }

}