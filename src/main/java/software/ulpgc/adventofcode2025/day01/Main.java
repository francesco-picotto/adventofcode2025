package software.ulpgc.adventofcode2025.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {

        List<String> instructions = loadInput("src/main/java/software/ulpgc/adventofcode2025/day01/input_day01.txt");

        int resultOne = solve(instructions, new BasicStrategy());
        int resultTwo = solve(instructions, new AdvancedStrategy());

        System.out.println("Part one result: " + resultOne);
        System.out.println("Part two result: " + resultTwo);
    }

    private static List<String> loadInput(String file) throws IOException {
        return Files.lines(Paths.get(file))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();
    }

    private static int solve(List<String> instructions, PasswordStrategy strategy) {
        Dial dial = new Dial();
        return instructions.stream()
                .mapToInt(instruction -> dial.rotate(instruction, strategy))
                .sum();
    }


}
