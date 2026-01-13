package software.ulpgc.adventofcode2025.days.day04.service;

import software.ulpgc.adventofcode2025.days.day04.rule.RemovalRule;

public class GridProcessor {
    private final RemovalRule rule;

    /**
     * Constructs a GridProcessor with the specified removal rule.
     *
     * Uses dependency injection to receive the rule that will determine
     * which cells should be removed from the grid.
     *
     * @param rule The rule to use for determining cell removal
     */
    public GridProcessor(RemovalRule rule) {
        this.rule = rule;
    }

    /**
     * Processes the grid by applying the removal rule and returns the count of removed cells.
     *
     * Delegates the entire removal logic to the configured rule, which analyzes
     * the grid and removes cells according to its specific criteria. The rule
     * returns the total number of cells that were removed.
     *
     * @param grid A 2D character array representing the grid to process
     * @return The total number of cells removed from the grid
     */
    public int solve(char[][] grid) {
        return rule.apply(grid);
    }
}