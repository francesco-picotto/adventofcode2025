package software.ulpgc.adventofcode2025.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
         char[][] grid1 = loadInput("src/main/java/software/ulpgc/adventofcode2025/day04/input_day04.txt");
         char[][] grid2 = loadInput("src/main/java/software/ulpgc/adventofcode2025/day04/input_day04.txt");

         int total1 = solve(grid1, new BasicRemovalRule());
         int total2 = solve(grid2, new AdvancedRemovalRule());
         System.out.println("Total 1: " + total1);
         System.out.println("Total 2: " + total2);

    }

    private static int solve(char[][] grid, RemovalRule rule) {
        return rule.apply(grid);
    }

    private static char[][] loadInput(String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }
}
