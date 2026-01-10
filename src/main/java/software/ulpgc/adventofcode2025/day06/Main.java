package software.ulpgc.adventofcode2025.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> lines = loadInput("src/main/java/software/ulpgc/adventofcode2025/day06/input_day06.txt");


        long result1 = new StandardColumnAnalyzer().analyze(lines);
        long result2 = new ReverseVerticalAnalyzer().analyze(lines);

        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
    }

    private static List<String> loadInput(String file) throws IOException {
        return Files.readAllLines(Paths.get(file));
    }
}
