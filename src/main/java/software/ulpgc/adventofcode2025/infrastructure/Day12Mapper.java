package software.ulpgc.adventofcode2025.infrastructure;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day12.*;

import java.util.*;
import java.util.stream.IntStream;

public class Day12Mapper implements InputMapper<PuzzleData> {
    @Override
    public PuzzleData map(List<String> lines) {
        // Identifica il punto di rottura (la prima riga che contiene una dimensione "x")
        int separatorIndex = IntStream.range(0, lines.size())
                .filter(i -> lines.get(i).contains("x"))
                .findFirst()
                .orElse(lines.size());

        return new PuzzleData(
                parseShapes(lines.subList(0, separatorIndex)),
                parseRegions(lines.subList(separatorIndex, lines.size()))
        );
    }

    private List<Shape> parseShapes(List<String> lines) {
        List<Shape> result = new ArrayList<>();
        Set<Point> currentPoints = new HashSet<>();
        int y = 0;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("[")) continue;

            if (trimmed.endsWith(":")) {
                if (!currentPoints.isEmpty()) result.add(new Shape(new HashSet<>(currentPoints)).normalize());
                currentPoints.clear();
                y = 0;
            } else {
                for (int x = 0; x < trimmed.length(); x++) {
                    if (trimmed.charAt(x) == '#') currentPoints.add(new Point(x, y));
                }
                y++;
            }
        }
        if (!currentPoints.isEmpty()) result.add(new Shape(currentPoints).normalize());
        return result;
    }

    private List<RegionRequest> parseRegions(List<String> lines) {
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
}
