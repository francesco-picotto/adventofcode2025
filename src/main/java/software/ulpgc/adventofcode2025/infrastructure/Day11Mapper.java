package software.ulpgc.adventofcode2025.infrastructure;


import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day11.ReactorMap;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day11Mapper implements InputMapper<ReactorMap> {
    @Override
    public ReactorMap map(List<String> lines){
        Map<String, Set<String>> connections =
                lines.stream()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty() && !line.startsWith("["))
                        .map(line -> line.split(":\\s*"))
                        .filter(parts -> parts.length >= 2)
                        .collect(Collectors.toMap(
                                parts -> parts[0].trim(),
                                parts -> Arrays.stream(parts[1].split("\\s+"))
                                        .filter(s->!s.isBlank())
                                        .collect(Collectors.toSet())
                        ));
        return new ReactorMap(connections);
    }
}
