package software.ulpgc.adventofcode2025.days.day04.rule;

import software.ulpgc.adventofcode2025.days.day04.domain.Grid;

/**
 * Basic implementation of RemovalRule that performs a single-pass removal of cells.
 *
 * This rule identifies '@' cells that have fewer than 4 '@' neighbors and marks
 * them for removal. All removals are identified first based on the current state,
 * then applied simultaneously, ensuring that the removal decision for each cell
 * is based on the original grid configuration.
 */
public class BasicRemovalRule implements RemovalRule {
    /**
     * Applies a single pass of the removal rule to the grid.
     *
     * The removal process occurs in two phases:
     * 1. Identification phase: Scans the entire grid and marks cells for removal
     *    based on the current state (cells are not modified during this phase)
     * 2. Application phase: Replaces all marked cells with 'X' simultaneously
     *
     * This two-phase approach ensures that removal decisions are based on the
     * grid's state before any removals, preventing cascade effects within a
     * single pass.
     *
     * @param cells A 2D character array representing the grid to process
     * @return The number of cells removed in this pass
     */
    @Override
    public int apply(char[][] cells){
        Grid grid = new Grid(cells);
        boolean[][] toRemove = new boolean[grid.rows()][grid.cols()];
        int count = 0;

        // First pass: identify what to remove based on current state
        for (int i = 0; i < grid.rows(); i++) {
            for (int j = 0; j < grid.cols(); j++) {
                if (shouldRemove(grid, i, j)) {
                    toRemove[i][j] = true;
                    count++;
                }
            }
        }
        applyRemovals(grid, toRemove);
        return count;
    }



    /**
     * Determines whether a cell should be removed based on the removal criteria.
     *
     * A cell is marked for removal if:
     * - It contains the character '@'
     * - It has fewer than 4 neighboring cells that also contain '@'
     *
     * This method is protected to allow subclasses to override the removal
     * criteria if needed.
     *
     * @param grid The grid being analyzed
     * @param r The row index of the cell to check
     * @param c The column index of the cell to check
     * @return true if the cell should be removed, false otherwise
     */
    protected boolean shouldRemove(Grid grid, int r, int c) {
        return grid.is(r, c, '@') && grid.countNeighbors(r, c, '@') < 4;
    }

    /**
     * Applies all marked removals to the grid by replacing cells with 'X'.
     *
     * Iterates through the removal matrix and replaces every cell marked as
     * true with the character 'X', effectively removing it from the active grid.
     *
     * @param grid The grid to modify
     * @param toRemove A boolean matrix indicating which cells to remove
     */
    private void applyRemovals(Grid grid, boolean[][] toRemove) {
        for(int i = 0; i < grid.rows(); i++) {
            for(int j = 0; j < grid.cols(); j++) {
                if(toRemove[i][j]) grid.set(i, j, 'X');
            }
        }
    }
}