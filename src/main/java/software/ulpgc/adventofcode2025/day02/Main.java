package software.ulpgc.adventofcode2025.day02;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class Main {

    public static void main(String[] args) throws Exception {

        List<String> ranges = loadInput("src/main/java/software/ulpgc/adventofcode2025/day02/input_day02.txt");


        InvalidRule part1Rule = new SimpleRepeatRule();
        InvalidRule part2Rule = new MultipleRepeatRule();

        long total1 = solve(ranges, part1Rule);
        long total2 = solve(ranges, part2Rule);

        System.out.println("Total part one: " + total1);
        System.out.println("Total part two: " + total2);
    }

    private static long solve(List<String> ranges, InvalidRule rule) {
        return ranges.stream()
                .mapToLong(range -> {
                    String[] parts = range.split("-");
                    long start = Long.parseLong(parts[0]);
                    long end = Long.parseLong(parts[1]);

                    return LongStream.rangeClosed(start, end)
                            .map(rule::evaluate)
                            .sum();
                })
                .sum();
    }


    private static List<String> loadInput(String filename) throws Exception {
        return Arrays.stream(Files.readString(Paths.get(filename)).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
