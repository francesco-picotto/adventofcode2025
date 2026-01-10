package software.ulpgc.adventofcode2025.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> manifoldDiagram = loadInput("src/main/java/software/ulpgc/adventofcode2025/day07/input_day07.txt");

        long result1 = new BeamSplitCounter().analyze(manifoldDiagram);
        long result2 = new QuantumTimelineEstimator().analyze(manifoldDiagram);
        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
    }

    private static List<String> loadInput(String file) throws IOException {
        return Files.readAllLines(Paths.get(file));
    }
}
