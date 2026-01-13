package software.ulpgc.adventofcode2025.days.day04.rule;

/**
 * Strategy interface for removing cells from a character grid.
 *
 * Implementations of this interface define different approaches to identifying
 * and removing cells from a grid based on specific criteria, such as the number
 * of neighboring cells or iterative removal patterns.
 */
public interface RemovalRule {
    /**
     * Applies the removal rule to the grid and returns the count of removed cells.
     *
     * Analyzes the grid according to the rule's specific criteria, removes cells
     * that meet the removal conditions, and counts how many cells were removed.
     * The grid is modified in place by replacing removed cells with 'X'.
     *
     * @param grid A 2D character array representing the grid to process
     * @return The total number of cells removed from the grid
     */
    int apply(char[][] grid);
}