package software.ulpgc.adventofcode2025.day11;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws IOException {
        ReactorMap map = loadInput("src/main/java/software/ulpgc/adventofcode2025/day11/input_day11.txt");

        long result1 = new SimplePathSolver().solve(map);
        long result2 = new MandatoryNodeSolver().solve(map);

        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
    }

    private static ReactorMap loadInput(String file) throws IOException {
        Map<String, Set<String>> connections =
                Files.lines(Paths.get(file))
                        .map(String::trim)
                        .filter(line -> !line.isEmpty() && !line.startsWith("["))
                        .map(line -> line.split(":\\s*"))
                        .filter(parts -> parts.length >= 2)
                        .collect(Collectors.toMap(
                                parts -> parts[0].trim(),
                                parts -> Arrays.stream(parts[1].split("\\s+"))
                                        .filter(s->!s.isBlank())
                                        .collect(Collectors.toSet())
                        ));
        return new ReactorMap(connections);

    }
}