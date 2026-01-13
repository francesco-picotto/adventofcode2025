package software.ulpgc.adventofcode2025.days.day09;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day09.analyzer.LoopRectangleAnalyzer;
import software.ulpgc.adventofcode2025.days.day09.analyzer.MaxRectangleAnalyzer;
import software.ulpgc.adventofcode2025.days.day09.service.GridProcessor;

/**
 * Main entry point for Day 9 of Advent of Code 2025.
 * This class demonstrates two different approaches to rectangle analysis:
 * 1. Finding the maximum rectangle area from any pair of tiles
 * 2. Finding the maximum valid rectangle area within a polygon boundary
 */
public class Main {

    /**
     * Executes both analysis strategies on the input data and prints the results.
     *
     * Part 1: Uses MaxRectangleAnalyzer to find the largest rectangle that can be
     *         formed by any pair of tiles without validation constraints.
     *
     * Part 2: Uses LoopRectangleAnalyzer to find the largest rectangle that is
     *         properly contained within the polygon formed by the tiles.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Load and parse the input file using the Day 9 mapper
        var input = new InputProvider("src/main/resources/inputs")
                .provide("input_day09.txt", new Day09Mapper());

        // Part 1: Calculate maximum rectangle area without validation
        System.out.println("result1: " + new GridProcessor(new MaxRectangleAnalyzer()).solve(input));

        // Part 2: Calculate maximum valid rectangle area within polygon boundary
        System.out.println("result2: " + new GridProcessor(new LoopRectangleAnalyzer()).solve(input));
    }
}