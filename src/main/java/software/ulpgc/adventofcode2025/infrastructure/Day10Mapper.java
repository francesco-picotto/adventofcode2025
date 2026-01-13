package software.ulpgc.adventofcode2025.infrastructure;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day10.InputParser;
import software.ulpgc.adventofcode2025.days.day10.Machine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day10Mapper implements InputMapper<List<Machine>> {
    @Override
    public List<Machine> map (List<String> lines){
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(InputParser::parseLine)
                .toList();
    }
}
