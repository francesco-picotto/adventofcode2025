package software.ulpgc.adventofcode2025.days.day05;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day05.analyzer.StockFreshnessChecker;
import software.ulpgc.adventofcode2025.days.day05.analyzer.TotalFreshCapacityEstimator;
import software.ulpgc.adventofcode2025.days.day05.service.InventoryProcessor;


public class Main {
    /**
     * Entry point of the application that analyzes inventory freshness data.
     *
     * Reads the input file containing fresh ingredient ranges and available ingredient IDs,
     * then processes them using two different analysis strategies:
     * - StockFreshnessChecker: Counts how many available IDs fall within fresh ranges
     * - TotalFreshCapacityEstimator: Calculates the total capacity of all fresh ranges
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){;
        var input = new InputProvider("src/main/resources/inputs").provide("input_day05.txt", new Day05Mapper());
        System.out.println("result1: " + new InventoryProcessor(new StockFreshnessChecker()).analyze(input.ranges(), input.ids()));
        System.out.println("result2: " + new InventoryProcessor(new TotalFreshCapacityEstimator()).analyze(input.ranges(), input.ids()));
    }
}