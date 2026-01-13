package software.ulpgc.adventofcode2025.days.day05;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.infrastructure.Day05Mapper;


public class Main {
    public static void main(String[] args){;
        var input = new InputProvider("src/main/resources/inputs").provide("input_day05.txt", new Day05Mapper());
        System.out.println("result1: " + new StockFreshnessChecker().analyze(input.ranges(), input.ids()));
        System.out.println("result2: " + new TotalFreshCapacityEstimator().analyze(input.ranges(), input.ids()));
    }
}
