package software.ulpgc.adventofcode2025.days.day07;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day07.analyzer.BeamSplitCounter;
import software.ulpgc.adventofcode2025.days.day07.analyzer.QuantumTimelineEstimator;
import software.ulpgc.adventofcode2025.days.day07.service.ManifoldProcessor;

public class Main {
    /**
     * Entry point of the application that analyzes tachyon beam behavior in a manifold grid.
     *
     * Reads the input file containing a grid representation of a tachyon manifold with
     * beam splitters, then processes it using two different analysis strategies:
     * - BeamSplitCounter: Counts the total number of beam splits that occur
     * - QuantumTimelineEstimator: Calculates the total number of quantum timelines created
     *
     * Beams start at a designated position ('S') and travel downward through the grid.
     * When a beam encounters a splitter ('^'), it splits into two beams going left and right.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        var input = new InputProvider("src/main/resources/inputs").provide("input_day07.txt", lines -> lines);
        System.out.println("result1: " + new ManifoldProcessor(new BeamSplitCounter()).solve(input));
        System.out.println("result2: " + new ManifoldProcessor(new QuantumTimelineEstimator()).solve(input));
    }
}