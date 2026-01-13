
package software.ulpgc.adventofcode2025.infrastructure;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day05.Day05Data;

import java.util.Arrays;
import java.util.List;

public class Day05Mapper implements InputMapper<Day05Data> {
    @Override
    public Day05Data map(List<String> lines) {
        String content = String.join("\n", lines);
        String[] sections = content.split("\\n\\s*\\n");

        return new Day05Data(
                Arrays.asList(sections[0].split("\\R")),
                Arrays.asList(sections[1].split("\\R"))
        );
    }
}