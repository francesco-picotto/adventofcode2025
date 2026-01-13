package software.ulpgc.adventofcode2025.days.day11;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day11.service.ReactorProcessor;
import software.ulpgc.adventofcode2025.days.day11.solver.MandatoryNodeSolver;
import software.ulpgc.adventofcode2025.days.day11.solver.SimplePathSolver;

/**
 * Main entry point for Day 11 of Advent of Code 2025.
 *
 * This class demonstrates two different approaches to reactor path analysis:
 * - Part 1: Simple path counting from start to exit
 * - Part 2: Path counting through mandatory intermediate nodes with ordering constraints
 *
 * Both parts analyze the same reactor network but with different routing requirements.
 */
public class Main {

    /**
     * Executes both path-finding solutions on the reactor map and prints the results.
     *
     * Part 1: Uses SimplePathSolver to count all distinct paths from the starting
     *         position ("you") to the exit ("out") without any constraints.
     *
     * Part 2: Uses MandatoryNodeSolver to find the maximum number of paths that
     *         pass through specific mandatory nodes (svr, fft, dac) in a required
     *         order, evaluating multiple possible orderings and selecting the best.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Load and parse the input file using the Day 11 mapper
        var input = new InputProvider("src/main/resources/inputs")
                .provide("input_day11.txt", new Day11Mapper());

        // Part 1: Count all paths from "you" to "out"
        System.out.println("result1: " +
                new ReactorProcessor(new SimplePathSolver()).solve(input));

        // Part 2: Find maximum paths through mandatory node sequences
        System.out.println("result2: " +
                new ReactorProcessor(new MandatoryNodeSolver()).solve(input));
    }
}