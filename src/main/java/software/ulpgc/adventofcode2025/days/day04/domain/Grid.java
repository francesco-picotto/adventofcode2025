package software.ulpgc.adventofcode2025.days.day04.domain;

/**
 * Represents a 2D character grid with utilities for cell access and neighbor counting.
 *
 * This class encapsulates a 2D character array and provides safe access methods
 * with boundary checking, as well as utilities for analyzing cell neighborhoods.
 * It serves as a domain object that simplifies grid operations.
 */
public class Grid {
    private final char[][] cells;
    private final int rows;
    private final int cols;

    /**
     * Constructs a Grid from a 2D character array.
     *
     * Creates a grid wrapper around the provided character array, calculating
     * and storing the dimensions for efficient access. The array is not copied,
     * so modifications through this Grid will affect the original array.
     *
     * @param cells A 2D character array representing the grid data
     */
    public Grid(char[][] cells) {
        this.cells = cells;
        this.rows = cells.length;
        this.cols = rows > 0 ? cells[0].length : 0;
    }

    /**
     * Returns the number of rows in the grid.
     *
     * @return The number of rows
     */
    public int rows() { return rows; }

    /**
     * Returns the number of columns in the grid.
     *
     * @return The number of columns
     */
    public int cols() { return cols; }

    /**
     * Retrieves the character at the specified position in the grid.
     *
     * If the position is outside the grid boundaries, returns a space character
     * instead of throwing an exception, providing safe access for boundary cases.
     *
     * @param r The row index
     * @param c The column index
     * @return The character at position (r, c), or ' ' if the position is invalid
     */
    public char get(int r, int c) {
        if (!isValid(r, c)) return ' ';
        return cells[r][c];
    }

    /**
     * Sets the character at the specified position in the grid.
     *
     * Only modifies the cell if the position is within valid grid boundaries.
     * Invalid positions are silently ignored.
     *
     * @param r The row index
     * @param c The column index
     * @param value The character value to set
     */
    public void set(int r, int c, char value) {
        if (isValid(r, c)) cells[r][c] = value;
    }

    /**
     * Checks if the cell at the specified position contains the given character.
     *
     * Uses the safe get() method, so positions outside the grid will compare
     * against the space character.
     *
     * @param r The row index
     * @param c The column index
     * @param value The character to compare against
     * @return true if the cell contains the specified character, false otherwise
     */
    public boolean is(int r, int c, char value) {
        return get(r, c) == value;
    }

    /**
     * Validates whether the given position is within the grid boundaries.
     *
     * @param r The row index to check
     * @param c The column index to check
     * @return true if the position is valid, false otherwise
     */
    private boolean isValid(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    /**
     * Counts how many of the 8 neighboring cells contain the target character.
     *
     * Examines all cells in a 3x3 area centered on the specified position
     * (excluding the center cell itself). Cells outside the grid boundaries
     * are treated as not matching the target character.
     *
     * The 8 neighbors are:
     * - Top-left, top, top-right
     * - Left, (center - excluded), right
     * - Bottom-left, bottom, bottom-right
     *
     * @param row The row of the cell whose neighbors to count
     * @param col The column of the cell whose neighbors to count
     * @param target The character to count in neighboring cells
     * @return The count of neighboring cells containing the target character (0-8)
     */
    public int countNeighbors(int row, int col, char target) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) continue;
                if (is(i, j, target)) count++;
            }
        }
        return count;
    }
}