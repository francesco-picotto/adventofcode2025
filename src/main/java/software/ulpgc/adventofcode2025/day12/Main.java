package software.ulpgc.adventofcode2025.day12;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var data = loadInput("src/main/java/software/ulpgc/adventofcode2025/day12/input_day12.txt");

        long result = data.regions().parallelStream()
                .filter(region -> {
                    // Creiamo la lista piatta dei regali richiesti per questa regione
                    List<Shape> required = flattenShapes(data.shapes(), region.counts());

                    // Euristiche di Pruning: se lo spazio occupato dai regali supera l'area, scarta subito
                    int totalPoints = required.stream().mapToInt(s -> s.points().size()).sum();
                    if (totalPoints > (region.w() * region.h())) return false;

                    // Motore di fitting con backtracking
                    return new FittingEngine(region.w(), region.h()).canFit(required);
                })
                .count();


                System.out.println("Result: " + result);
    }

    private static List<Shape> flattenShapes(List<Shape> shapes, List<Integer> counts) {
        return IntStream.range(0, counts.size())
                .boxed()
                .flatMap(i -> Collections.nCopies(counts.get(i), shapes.get(i)).stream())
                .toList();
    }

    static PuzzleData loadInput(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        // Identifica il punto di rottura tra la sezione Forme e la sezione Regioni
        int separatorIndex = IntStream.range(0, lines.size())
                .filter(i -> lines.get(i).contains("x"))
                .findFirst()
                .orElse(lines.size());

        return new PuzzleData(
                parseShapes(lines.subList(0, separatorIndex)),
                parseRegions(lines.subList(separatorIndex, lines.size()))
        );
    }

    private static List<RegionRequest> parseRegions(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(l -> l.contains("x"))
                .map(line -> {
                    String[] parts = line.split(": ");
                    String[] dims = parts[0].split("x");
                    List<Integer> counts = Arrays.stream(parts[1].split("\\s+"))
                            .map(Integer::parseInt).toList();
                    return new RegionRequest(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]), counts);
                }).toList();
    }

    private static List<Shape> parseShapes(List<String> lines) {
        List<Shape> result = new ArrayList<>();
        Set<Point> currentPoints = new HashSet<>();
        int y = 0;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("[")) continue;

            if (trimmed.endsWith(":")) {
                if (!currentPoints.isEmpty()) result.add(new Shape(new HashSet<>(currentPoints)));
                currentPoints.clear();
                y = 0;
            } else {
                for (int x = 0; x < trimmed.length(); x++) {
                    if (trimmed.charAt(x) == '#') currentPoints.add(new Point(x, y));
                }
                y++;
            }
        }
        if (!currentPoints.isEmpty()) result.add(new Shape(currentPoints));
        return result;
    }


}