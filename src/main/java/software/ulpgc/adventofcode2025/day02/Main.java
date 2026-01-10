package software.ulpgc.adventofcode2025.day02;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class Main {

    public static void main(String[] args) throws Exception {

        List<String> ranges = loadInput("src/main/java/software/ulpgc/adventofcode2025/day02/input_day02.txt");


        IdRule part1Rule = new SimpleRepeatRule();
        IdRule part2Rule = new MultipleRepeatRule();

        long total1 = solve(ranges, part1Rule);
        long total2 = solve(ranges, part2Rule);

        System.out.println("Total part one: " + total1);
        System.out.println("Total part two: " + total2);
    }

    private static long solve(List<String> ranges, IdRule rule) {
        return ranges.stream()
                .map(IdRange::parse)
                .mapToLong(r -> r.sumValidIds(rule))
                .sum();
    }


    private static List<String> loadInput(String file) throws Exception {
        return Arrays.stream(Files.readString(Paths.get(file)).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
