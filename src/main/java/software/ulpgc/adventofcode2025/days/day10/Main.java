package software.ulpgc.adventofcode2025.days.day10;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day10.service.MachineProcessor;
import software.ulpgc.adventofcode2025.days.day10.solver.JoltageSolver;
import software.ulpgc.adventofcode2025.days.day10.solver.LightConfigurationSolver;

/**
 * Main entry point for Day 10 of Advent of Code 2025.
 *
 * This class solves two different types of machine puzzles:
 * - Part A: Light configuration puzzles using BFS to find minimum button presses
 * - Part B: Joltage puzzles using dynamic programming to reduce values to zero
 *
 * Both parts process multiple machines and sum the total button presses required.
 */
public class Main {

    /**
     * Executes both puzzle solutions on the input data and prints the results.
     *
     * Part A: Uses LightConfigurationSolver to find the minimum number of button
     *         presses needed to achieve target light configurations across all machines.
     *         Uses breadth-first search to explore the state space.
     *
     * Part B: Uses JoltageSolver to find the minimum number of button presses needed
     *         to reduce all joltage values to zero across all machines. Uses dynamic
     *         programming with memoization to optimize the recursive computation.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Load and parse the input file using the Day 10 mapper
        var input = new InputProvider("src/main/resources/inputs")
                .provide("input_day10.txt", new Day10Mapper());

        // Part A: Solve light configuration puzzles
        System.out.println("Day 10 Part A: " + new MachineProcessor(new LightConfigurationSolver()).solve(input));

        // Part B: Solve joltage puzzles
        System.out.println("Day 10 Part B: " + new MachineProcessor(new JoltageSolver()).solve(input));
    }
}