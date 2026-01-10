package software.ulpgc.adventofcode2025.day10;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputParser {

    // Identifica i tre tipi di blocchi: [luci], (bottoni), {joltage}
    private static final Pattern COMPONENT_PATTERN = Pattern.compile("\\[[.#]+\\]|\\([^)]+\\)|\\{[^}]+\\}");

    public static Machine parseLine(String line) {
        // Estraiamo tutti i componenti presenti nella riga
        List<String> components = COMPONENT_PATTERN.matcher(line)
                .results()
                .map(MatchResult::group)
                .toList();

        // 1. Parsing Luci: [.##.] -> [false, true, true, false]
        List<Boolean> lights = components.stream()
                .filter(s -> s.startsWith("["))
                .flatMap(s -> s.substring(1, s.length() - 1).chars().mapToObj(c -> c == '#'))
                .toList();

        // 2. Parsing Bottoni: (1,3) -> Set di {1, 3}
        List<Set<Integer>> buttons = components.stream()
                .filter(s -> s.startsWith("("))
                .map(InputParser::parseCsvToSet)
                .toList();

        // 3. Parsing Joltage: {3,5,4,7} -> List di [3, 5, 4, 7]
        List<Integer> joltage = components.stream()
                .filter(s -> s.startsWith("{"))
                .flatMap(s -> Arrays.stream(s.substring(1, s.length() - 1).split(","))
                        .map(String::trim)
                        .filter(v -> !v.isEmpty())
                        .map(Integer::parseInt))
                .toList();

        return new Machine(lights, buttons, joltage);
    }

    private static Set<Integer> parseCsvToSet(String s) {
        String content = s.substring(1, s.length() - 1);
        if (content.isBlank()) return Collections.emptySet();
        return Arrays.stream(content.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }
}
