package software.ulpgc.adventofcode2025.days.day04;

import software.ulpgc.adventofcode2025.utils.GridUtils;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day04.rule.AdvancedRemovalRule;
import software.ulpgc.adventofcode2025.days.day04.rule.BasicRemovalRule;
import software.ulpgc.adventofcode2025.days.day04.service.GridProcessor;


public class Main {
    /**
     * Entry point of the application that processes a character grid to remove cells.
     *
     * Reads the input file containing a 2D character grid, then processes it using
     * two different removal strategies: BasicRemovalRule (single-pass removal) and
     * AdvancedRemovalRule (iterative removal until no more cells can be removed).
     *
     * The grid is copied before each processing to ensure both rules work with
     * the same initial state.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        var input = new InputProvider("src/main/resources/inputs").provide("input_day04.txt", new Day04Mapper());
        System.out.println("Total 1: " + new GridProcessor(new BasicRemovalRule()).solve(GridUtils.copy(input)));
        System.out.println("Total 2: " + new GridProcessor(new AdvancedRemovalRule(new BasicRemovalRule())).solve(GridUtils.copy(input)));
    }
}