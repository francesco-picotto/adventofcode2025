package software.ulpgc.adventofcode2025.days.day09;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day09.domain.Tile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Input mapper for Day 9 that parses 2D coordinate data into Tile objects.
 * This mapper converts comma-separated x,y coordinate pairs into a list of
 * Tile domain objects representing positions in a 2D grid.
 */
public class Day09Mapper implements InputMapper<List<Tile>> {

    /**
     * Maps input lines containing comma-separated coordinates into a list of Tile objects.
     *
     * Each line should contain two comma-separated integer values representing x and y
     * coordinates. Empty lines are filtered out, and all values are trimmed before parsing.
     *
     * Processing steps:
     * 1. Trim whitespace from each line
     * 2. Filter out empty lines
     * 3. Split each line by comma to extract x and y coordinates
     * 4. Parse coordinates as integers and create Tile objects
     * 5. Collect all tiles into a list
     *
     * Example:
     * Input lines: ["10,20", "30,40", "", "50,60"]
     * Output: [Tile(10,20), Tile(30,40), Tile(50,60)]
     *
     * @param lines the raw input lines from the file
     * @return a list of Tile objects, one per valid coordinate pair
     */
    @Override
    public List<Tile> map(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    // Split the line into x and y coordinates
                    String[] coords = line.split(",");

                    // Create a new Tile with parsed coordinates
                    return new Tile(
                            Integer.parseInt(coords[0]),
                            Integer.parseInt(coords[1])
                    );
                })
                .collect(Collectors.toList());
    }
}