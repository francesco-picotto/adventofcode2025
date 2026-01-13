package software.ulpgc.adventofcode2025.days.day04;

import software.ulpgc.adventofcode2025.core.InputMapper;

import java.util.List;

/**
 * Input mapper that converts text lines into a 2D character grid.
 * This mapper is useful for puzzles that operate on character-based grids,
 * such as maze navigation, pattern matching, or cellular automata problems.
 */
public class Day04Mapper implements InputMapper<char[][]> {

    /**
     * Converts a list of text lines into a 2D character array (grid).
     * Each line becomes a row in the resulting grid, with each character
     * in the line becoming an individual cell.
     *
     * Example:
     * Input lines: ["ABC", "DEF", "GHI"]
     * Output grid: [['A','B','C'], ['D','E','F'], ['G','H','I']]
     *
     * @param lines the input text lines to convert
     * @return a 2D char array where each row represents a line from the input
     */
    @Override
    public char[][] map(List<String> lines) {
        return lines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }
}