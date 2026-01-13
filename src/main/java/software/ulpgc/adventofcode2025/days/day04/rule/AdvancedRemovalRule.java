package software.ulpgc.adventofcode2025.days.day04.rule;

/**
 * Advanced implementation of RemovalRule that performs iterative removal until stable.
 *
 * This rule applies a basic removal rule repeatedly until no more cells can be
 * removed. This creates a cascade effect where removing cells in one iteration
 * may cause other cells to become eligible for removal in subsequent iterations.
 * The process continues until the grid reaches a stable state.
 */
public class AdvancedRemovalRule implements RemovalRule {

    private final RemovalRule basicRule;

    /**
     * Constructs an AdvancedRemovalRule that uses the specified basic rule iteratively.
     *
     * Uses composition to delegate the actual removal logic to a basic rule,
     * while adding the iterative behavior on top. This allows the advanced rule
     * to work with any RemovalRule implementation.
     *
     * @param basicRule The basic removal rule to apply iteratively
     */
    public AdvancedRemovalRule(RemovalRule basicRule) {
        this.basicRule = basicRule;
    }

    /**
     * Applies the removal rule iteratively until no more cells can be removed.
     *
     * Repeatedly applies the basic rule to the grid, accumulating the count of
     * removed cells from each iteration. The process continues until a pass
     * removes zero cells, indicating the grid has reached a stable state where
     * no cells meet the removal criteria.
     *
     * This iterative approach allows for cascade effects where:
     * 1. Initial cells are removed in the first pass
     * 2. Their removal may cause neighboring cells to lose support
     * 3. These newly vulnerable cells are removed in subsequent passes
     * 4. The process repeats until the grid stabilizes
     *
     * @param grid A 2D character array representing the grid to process
     * @return The total number of cells removed across all iterations
     */
    @Override
    public int apply(char[][] grid) {
        int count = 0;
        int currentBatch;

        while((currentBatch = basicRule.apply(grid)) > 0) {
            count += currentBatch;
        }
        return count;
    }
}