package software.ulpgc.adventofcode2025.days.day10;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.infrastructure.Day10Mapper;


public class Main {

    public static void main(String[] args){
        var input = new InputProvider("src/main/resources/inputs").provide("input_day10.txt", new Day10Mapper());

        System.out.println("Day 10 Part A: " + new MachineProcessor(new LightConfigurationSolver()).solve(input));
        System.out.println("Day 10 Part B: " + new MachineProcessor(new JoltageSolver()).solve(input));
    }


}
