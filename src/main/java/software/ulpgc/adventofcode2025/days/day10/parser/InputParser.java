package software.ulpgc.adventofcode2025.days.day10.parser;

import software.ulpgc.adventofcode2025.days.day10.domain.Machine;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parser utility class for converting text input into Machine domain objects.
 *
 * This parser recognizes three types of components in the input format:
 * - Lights: [.##.] where '.' = off (false) and '#' = on (true)
 * - Buttons: (1,3) representing sets of indices affected by each button
 * - Joltage: {3,5,4,7} representing target joltage values
 */
public class InputParser {

    /**
     * Regular expression pattern to identify three types of components:
     * - [lights] enclosed in square brackets containing dots and hashes
     * - (buttons) enclosed in parentheses containing comma-separated numbers
     * - {joltage} enclosed in curly braces containing comma-separated numbers
     */
    private static final Pattern COMPONENT_PATTERN = Pattern.compile("\\[[.#]+\\]|\\([^)]+\\)|\\{[^}]+\\}");

    /**
     * Parses a single line of input text into a Machine object.
     * The line can contain any combination of lights, buttons, and joltage components
     * in any order. All matching components are extracted and parsed according to their type.
     *
     * Example input formats:
     * - "[.##.](1,3)(0,2){3,5,4,7}"
     * - "{10,20,30}(0,1,2)[##.]"
     *
     * @param line the input line to parse
     * @return a Machine object containing the parsed lights, buttons, and joltage values
     */
    public static Machine parseLine(String line) {
        // Extract all components present in the line
        List<String> components = COMPONENT_PATTERN.matcher(line)
                .results()
                .map(MatchResult::group)
                .toList();

        // 1. Parse Lights: [.##.] -> [false, true, true, false]
        //    Dots represent lights that are off, hashes represent lights that are on
        List<Boolean> lights = components.stream()
                .filter(s -> s.startsWith("["))
                .flatMap(s -> s.substring(1, s.length() - 1).chars().mapToObj(c -> c == '#'))
                .toList();

        // 2. Parse Buttons: (1,3) -> Set of {1, 3}
        //    Each button specification becomes a set of indices it affects
        List<Set<Integer>> buttons = components.stream()
                .filter(s -> s.startsWith("("))
                .map(InputParser::parseCsvToSet)
                .toList();

        // 3. Parse Joltage: {3,5,4,7} -> List of [3, 5, 4, 7]
        //    Joltage values are extracted as integers and maintained in order
        List<Integer> joltage = components.stream()
                .filter(s -> s.startsWith("{"))
                .flatMap(s -> Arrays.stream(s.substring(1, s.length() - 1).split(","))
                        .map(String::trim)
                        .filter(v -> !v.isEmpty())
                        .map(Integer::parseInt))
                .toList();

        return new Machine(lights, buttons, joltage);
    }

    /**
     * Parses a comma-separated list of integers enclosed in parentheses into a Set.
     * Handles empty input gracefully by returning an empty set.
     *
     * Example: "(1,3,5)" -> {1, 3, 5}
     *
     * @param s the string to parse (format: "(num1,num2,num3)")
     * @return a Set of integers parsed from the input
     */
    private static Set<Integer> parseCsvToSet(String s) {
        // Remove the enclosing parentheses
        String content = s.substring(1, s.length() - 1);

        // Handle empty button specification
        if (content.isBlank()) return Collections.emptySet();

        // Split by comma, trim whitespace, parse integers, and collect into a set
        return Arrays.stream(content.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }
}