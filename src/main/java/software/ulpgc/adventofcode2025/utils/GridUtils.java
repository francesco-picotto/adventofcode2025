package software.ulpgc.adventofcode2025.utils;

/**
 * Utility class providing helper methods for working with 2D character grids.
 * This class contains static methods for common grid operations such as copying,
 * which are frequently needed in grid-based puzzle solving.
 */
public class GridUtils {

    /**
     * Creates a deep copy of a 2D character array (grid).
     *
     * This method performs a deep copy, meaning each row array is cloned independently,
     * ensuring that modifications to the copy do not affect the original grid.
     * This is essential when you need to preserve the original state while testing
     * different transformations or solutions.
     *
     * The method handles null input gracefully by returning null.
     *
     * Implementation notes:
     * - Uses clone() for each row to efficiently copy array contents
     * - Creates a new outer array to hold the row references
     * - Each inner array is independently cloned to prevent shared references
     *
     * Example usage:
     * <pre>
     * char[][] original = {{'A', 'B'}, {'C', 'D'}};
     * char[][] copy = GridUtils.copy(original);
     * copy[0][0] = 'X';  // original[0][0] remains 'A'
     * </pre>
     *
     * @param original the 2D character array to copy, may be null
     * @return a deep copy of the original grid, or null if input is null
     */
    public static char[][] copy(char[][] original) {
        // Handle null input
        if (original == null) return null;

        // Create a new outer array with the same length
        char[][] copy = new char[original.length][];

        // Clone each row independently to ensure deep copy
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }

        return copy;
    }
}