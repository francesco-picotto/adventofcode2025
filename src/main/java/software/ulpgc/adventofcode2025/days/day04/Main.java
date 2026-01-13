package software.ulpgc.adventofcode2025.days.day04;

import software.ulpgc.adventofcode2025.Utils.GridUtils;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.infrastructure.GridMapper;


public class Main {
    public static void main(String[] args) {
        var input = new InputProvider("src/main/resources/inputs").provide("input_day04.txt", new GridMapper());
        System.out.println("Total 1: " + new GridProcessor(new BasicRemovalRule()).solve(GridUtils.copy(input)));
        System.out.println("Total 2: " + new GridProcessor(new AdvancedRemovalRule(new BasicRemovalRule())).solve(GridUtils.copy(input)));
    }
}
