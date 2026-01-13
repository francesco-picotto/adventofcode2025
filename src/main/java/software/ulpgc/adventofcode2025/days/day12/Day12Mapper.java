package software.ulpgc.adventofcode2025.days.day12;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day12.domain.Point;
import software.ulpgc.adventofcode2025.days.day12.domain.PuzzleData;
import software.ulpgc.adventofcode2025.days.day12.domain.RegionRequest;
import software.ulpgc.adventofcode2025.days.day12.domain.Shape;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Input mapper for Day 12 that parses shape packing puzzle data.
 *
 * This mapper handles a complex input format with two distinct sections:
 * 1. Shape definitions: ASCII art representations of geometric shapes
 * 2. Region requests: Target dimensions and shape count requirements
 *
 * The two sections are separated by the first line containing an 'x' character
 * (indicating dimension specifications like "5x3").
 */
public class Day12Mapper implements InputMapper<PuzzleData> {

    /**
     * Maps input lines into a PuzzleData object containing shape definitions and region requests.
     *
     * The input format consists of two sections:
     *
     * Section 1 - Shape Definitions (before first line with 'x'):
     * ShapeName:
     * ###
     * #..
     *
     * Section 2 - Region Requests (lines with 'x'):
     * 5x3: 2 1 0  (means: region of 5x3 needs 2 of shape[0], 1 of shape[1], 0 of shape[2])
     *
     * @param lines the raw input lines from the file
     * @return a PuzzleData object containing parsed shapes and region requests
     */
    @Override
    public PuzzleData map(List<String> lines) {
        // Find the separator between shape definitions and region requests
        // The first line containing 'x' (dimension specification) marks the boundary
        int separatorIndex = IntStream.range(0, lines.size())
                .filter(i -> lines.get(i).contains("x"))
                .findFirst()
                .orElse(lines.size());

        return new PuzzleData(
                parseShapes(lines.subList(0, separatorIndex)),
                parseRegions(lines.subList(separatorIndex, lines.size()))
        );
    }

    /**
     * Parses shape definitions from ASCII art format into Shape objects.
     *
     * Format:
     * - Each shape starts with a name line ending in ':'
     * - Subsequent lines contain '#' for filled cells and '.' for empty cells
     * - A blank line or new name line indicates the end of current shape
     * - Lines starting with '[' are treated as comments/metadata and skipped
     *
     * The parser:
     * 1. Accumulates points (x,y coordinates where '#' appears) for current shape
     * 2. When encountering a new shape name or end of section, finalizes current shape
     * 3. Normalizes each shape to ensure top-left corner is at origin
     *
     * Example:
     * L-Shape:
     * #..
     * #..
     * ##.
     *
     * Results in: Shape with points {(0,0), (0,1), (0,2), (1,2)}, then normalized
     *
     * @param lines the lines containing shape definitions
     * @return a list of normalized Shape objects
     */
    private List<Shape> parseShapes(List<String> lines) {
        List<Shape> result = new ArrayList<>();
        Set<Point> currentPoints = new HashSet<>();
        int y = 0;

        for (String line : lines) {
            String trimmed = line.trim();

            // Skip empty lines and comment/metadata lines
            if (trimmed.isEmpty() || trimmed.startsWith("[")) continue;

            if (trimmed.endsWith(":")) {
                // New shape definition started - finalize previous shape if exists
                if (!currentPoints.isEmpty()) {
                    result.add(new Shape(new HashSet<>(currentPoints)).normalize());
                }
                currentPoints.clear();
                y = 0;
            } else {
                // Parse the line as shape data, marking '#' positions as points
                for (int x = 0; x < trimmed.length(); x++) {
                    if (trimmed.charAt(x) == '#') {
                        currentPoints.add(new Point(x, y));
                    }
                }
                y++;
            }
        }

        // Add the last shape if any points were accumulated
        if (!currentPoints.isEmpty()) {
            result.add(new Shape(currentPoints).normalize());
        }

        return result;
    }

    /**
     * Parses region request specifications into RegionRequest objects.
     *
     * Format: "widthxheight: count1 count2 count3 ..."
     * Example: "5x3: 2 1 0"
     * Meaning: A 5x3 region requires 2 of shape[0], 1 of shape[1], and 0 of shape[2]
     *
     * Processing:
     * 1. Filter lines to those containing 'x' (dimension specification)
     * 2. Split on ':' to separate dimensions from counts
     * 3. Parse dimensions (widthxheight)
     * 4. Parse shape counts as whitespace-separated integers
     * 5. Create RegionRequest with dimensions and count list
     *
     * @param lines the lines containing region request specifications
     * @return a list of RegionRequest objects
     */
    private List<RegionRequest> parseRegions(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(l -> l.contains("x"))  // Only process dimension specification lines
                .map(line -> {
                    // Split into dimensions and counts: "5x3: 2 1 0" → ["5x3", "2 1 0"]
                    String[] parts = line.split(": ");

                    // Parse dimensions: "5x3" → [5, 3]
                    String[] dims = parts[0].split("x");

                    // Parse shape counts: "2 1 0" → [2, 1, 0]
                    List<Integer> counts = Arrays.stream(parts[1].split("\\s+"))
                            .map(Integer::parseInt)
                            .toList();

                    return new RegionRequest(
                            Integer.parseInt(dims[0]),  // width
                            Integer.parseInt(dims[1]),  // height
                            counts                       // shape counts
                    );
                })
                .toList();
    }
}