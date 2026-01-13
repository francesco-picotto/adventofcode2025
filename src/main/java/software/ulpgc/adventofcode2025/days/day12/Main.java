package software.ulpgc.adventofcode2025.days.day12;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day12.service.PackingProcessor;
import software.ulpgc.adventofcode2025.days.day12.strategy.BacktrackingFittingStrategy;

/**
 * Main entry point for Day 12 of Advent of Code 2025.
 *
 * This class solves a shape packing puzzle where geometric pieces must be arranged
 * to fit into rectangular regions. The solution uses a backtracking algorithm to
 * explore all possible placements and orientations of shapes.
 *
 * The puzzle resembles classic polyomino packing problems like Tetris or Pentominoes,
 * where the challenge is determining which regions can successfully accommodate
 * their required collection of shapes.
 */
public class Main {

    /**
     * Executes the packing puzzle solver on the input data and prints the result.
     *
     * The solver uses BacktrackingFittingStrategy to exhaustively search for valid
     * arrangements of shapes within each region. It processes multiple regions in
     * parallel and counts how many can be successfully filled with their required shapes.
     *
     * The algorithm considers all rotations and reflections of each shape, ensuring
     * that every possible orientation is tried during the fitting process.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Load and parse the input file using the Day 12 mapper
        var input = new InputProvider("src/main/resources/inputs")
                .provide("input_day12.txt", new Day12Mapper());

        // Solve the packing puzzle and display the count of successfully filled regions
        System.out.println("Result: " + new PackingProcessor(new BacktrackingFittingStrategy()).solve(input));
    }
}