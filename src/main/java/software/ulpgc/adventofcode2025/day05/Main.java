package software.ulpgc.adventofcode2025.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] blocks = loadInput("src/main/java/software/ulpgc/adventofcode2025/day05/input_day05.txt");

        List<String> rangeString = Arrays.asList(blocks[0].split("\\R"));
        List<String> availableIds = Arrays.asList(blocks[1].split("\\R"));

        long result1 = new StockFreshnessChecker().analyze(rangeString, availableIds);
        long result2 = new TotalFreshCapacityEstimator().analyze(rangeString, availableIds);

        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);


    }

    private static String[] loadInput(String file) throws IOException {
        return  Files.readString(Paths.get(file)).split("\\R\\R");
    }
}
