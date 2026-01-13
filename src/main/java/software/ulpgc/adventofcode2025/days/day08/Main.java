package software.ulpgc.adventofcode2025.days.day08;

import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day10.InputParser;
import software.ulpgc.adventofcode2025.infrastructure.Day08Mapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args){
        var input = new InputProvider("src/main/resources/inputs").provide("input_day08.txt", new Day08Mapper());
        System.out.println("result1: " + new CircuitProcessor(new BasicCircuitAnalyzer()).solve(input));
        System.out.println("result2: " + new CircuitProcessor(new AdvanceCircuitAnalyzer()).solve(input));
    }
}
