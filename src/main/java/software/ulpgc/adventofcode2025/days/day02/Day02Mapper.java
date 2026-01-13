package software.ulpgc.adventofcode2025.days.day02;

import software.ulpgc.adventofcode2025.core.InputMapper;

import java.util.Arrays;
import java.util.List;

/**
 * Input mapper for Day 2 that parses comma-separated values from multiple lines
 * and flattens them into a single list.
 *
 * This mapper processes input where each line may contain multiple comma-separated
 * values and combines all values into one continuous list. It's useful for
 * parsing compact data formats where multiple values are stored per line.
 */
public class Day02Mapper implements InputMapper<List<String>> {

    /**
     * Maps input lines containing comma-separated values into a flat list of strings.
     *
     * Processing steps:
     * 1. Trims whitespace from each line
     * 2. Filters out empty lines
     * 3. Splits each line by commas to get individual values
     * 4. Flattens all arrays into a single stream
     * 5. Collects into a single list
     *
     * Example:
     * Input lines: ["1102,2949", "3000,4500", "5000"]
     * Output list: ["1102", "2949", "3000", "4500", "5000"]
     *
     * @param lines the raw input lines from the file
     * @return a flattened list of all comma-separated values from all lines
     */
    @Override
    public List<String> map(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> line.split(","))  // Transform each line into an array ["1102", "2949"]
                .flatMap(Arrays::stream)       // Flatten the arrays into a single stream of strings
                .toList();
    }
}