package software.ulpgc.adventofcode2025.day10;

import software.ulpgc.adventofcode2025.day09.Tile;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Machine> machines = loadInput("src/main/java/software/ulpgc/adventofcode2025/day10/input_day10.txt");

        long result1 = solveAll(machines, new LightConfigurationSolver());
        long result2 = solveAll(machines, new JoltageSolver());

        System.out.println("Risultato Parte 1: " + result1);
        System.out.println("Risultato Parte 2: " + result2);
    }

    private static long solveAll(List<Machine> machines, MachineSolver solver) {
        return machines.stream().mapToLong(solver::solve).sum();
    }


    static List<Machine> loadInput(String file) throws IOException {
        return Files.lines(Paths.get(file))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(InputParser::parseLine) // <-- Qui "nascondi" la logica complessa
                .toList();
    }
}
