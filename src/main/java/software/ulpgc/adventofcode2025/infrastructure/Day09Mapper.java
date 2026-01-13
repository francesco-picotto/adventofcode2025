package software.ulpgc.adventofcode2025.infrastructure;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day09.Tile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day09Mapper implements InputMapper<List<Tile>> {
    @Override
   public List<Tile> map(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line ->{
                    String[] coords = line.split(",");
                    return new Tile(
                            Integer.parseInt(coords[0]),
                            Integer.parseInt(coords[1])
                    );
                })
                .collect(Collectors.toList());
    }
}

