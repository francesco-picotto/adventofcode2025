package software.ulpgc.adventofcode2025.days.day07;
import software.ulpgc.adventofcode2025.core.InputProvider;

public class Main {
    public static void main(String[] args) {
        var input = new InputProvider("src/main/resources/inputs").provide("input_day07.txt", lines -> lines);
        System.out.println("result1: " + new ManifoldProcessor(new BeamSplitCounter()).solve(input));
        System.out.println("result2: " + new ManifoldProcessor(new QuantumTimelineEstimator()).solve(input));
    }
}
