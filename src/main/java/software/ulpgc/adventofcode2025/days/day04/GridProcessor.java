package software.ulpgc.adventofcode2025.days.day04;

import java.util.List;

public class GridProcessor {
    private final RemovalRule rule;

    public GridProcessor(RemovalRule rule) {
        this.rule = rule;
    }

    public int solve(char[][] grid) {
        return rule.apply(grid);
    }
}
