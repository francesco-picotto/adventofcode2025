package software.ulpgc.adventofcode2025.days.day10;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day10.parser.InputParser;
import software.ulpgc.adventofcode2025.days.day10.domain.Machine;

import java.util.List;

/**
 * Input mapper for Day 10 that parses machine configuration data.
 * This mapper delegates the complex parsing logic to the InputParser utility,
 * which handles the specific format of machine specifications including lights,
 * buttons, and joltage values.
 */
public class Day10Mapper implements InputMapper<List<Machine>> {

    /**
     * Maps input lines into a list of Machine objects.
     *
     * Each line represents a complete machine configuration with lights, buttons,
     * and joltage targets encoded in a specific format. The actual parsing logic
     * is delegated to InputParser.parseLine() which understands the machine
     * specification format.
     *
     * Processing steps:
     * 1. Trim whitespace from each line
     * 2. Filter out empty lines
     * 3. Parse each line using InputParser to create a Machine object
     * 4. Collect all machines into a list
     *
     * Example input line format: "[.##.](1,3)(0,2){3,5,4,7}"
     * Where:
     * - [.##.] represents light states
     * - (1,3) and (0,2) represent button effects
     * - {3,5,4,7} represents target joltage values
     *
     * @param lines the raw input lines from the file
     * @return a list of Machine objects, one per valid configuration line
     */
    @Override
    public List<Machine> map(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(InputParser::parseLine)
                .toList();
    }
}