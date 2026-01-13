package software.ulpgc.adventofcode2025.infrastructure;

import software.ulpgc.adventofcode2025.core.InputMapper;

import java.util.List;

public class GridMapper implements InputMapper<char[][]> {
    @Override
    public char[][] map(List<String> lines) {
        return lines.stream().map(String::toCharArray).toArray(char[][]::new);
    }
}
