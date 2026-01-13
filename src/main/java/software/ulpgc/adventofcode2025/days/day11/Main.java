package software.ulpgc.adventofcode2025.days.day11;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.infrastructure.Day11Mapper;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args){

        var input = new InputProvider("src/main/resources/inputs").provide("input_day11.txt", new Day11Mapper());
        System.out.println("result1: " + new ReactorProcessor(new SimplePathSolver()).solve(input));
        System.out.println("result2: " + new ReactorProcessor(new MandatoryNodeSolver()).solve(input));
    }
}