package software.ulpgc.adventofcode2025.days.day08;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day08.analyzer.AdvanceCircuitAnalyzer;
import software.ulpgc.adventofcode2025.days.day08.analyzer.BasicCircuitAnalyzer;
import software.ulpgc.adventofcode2025.days.day08.service.CircuitProcessor;

public class Main {
    /**
     * Entry point of the application that analyzes circuit connectivity.
     *
     * Reads the input file containing junction box positions and possible connections,
     * then processes them using two different analysis strategies:
     * - BasicCircuitAnalyzer: Forms components using the first 1000 connections and
     *   calculates the product of the three largest component sizes
     * - AdvanceCircuitAnalyzer: Progressively adds connections until all boxes form
     *   a single connected component, then returns a result based on the final connection
     *
     * Both analyzers use Union-Find data structure to efficiently track component
     * connectivity as connections are added.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        var input = new InputProvider("src/main/resources/inputs").provide("input_day08.txt", new Day08Mapper());
        System.out.println("result1: " + new CircuitProcessor(new BasicCircuitAnalyzer()).solve(input));
        System.out.println("result2: " + new CircuitProcessor(new AdvanceCircuitAnalyzer()).solve(input));
    }
}