package software.ulpgc.adventofcode2025.days.day12;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.infrastructure.Day12Mapper;

public class Main {
    public static void main(String[] args) throws Exception {
        var input = new InputProvider("src/main/resources/inputs").provide("input_day12.txt", new Day12Mapper());
        System.out.println("Result: " + new PackingProcessor(new BacktrackingFittingStrategy()).solve(input));
    }
}