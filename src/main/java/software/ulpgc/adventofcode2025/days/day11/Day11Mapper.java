package software.ulpgc.adventofcode2025.days.day11;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day11.domain.ReactorMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Input mapper for Day 11 that parses reactor network adjacency data into a ReactorMap.
 * This mapper converts text-based graph representations into a structured map of node
 * connections, creating a directed graph data structure.
 *
 * The expected input format is a simple adjacency list where each line defines a node
 * and its outgoing connections.
 */
public class Day11Mapper implements InputMapper<ReactorMap> {

    /**
     * Maps input lines into a ReactorMap representing a directed graph.
     *
     * Expected input format:
     * Each line: "node: target1 target2 target3"
     * Where:
     * - "node" is the source node name
     * - Colon separates the node from its targets
     * - Target nodes are space-separated
     *
     * Processing rules:
     * 1. Trim whitespace from each line
     * 2. Filter out empty lines and comment lines (starting with '[')
     * 3. Split each line on the colon to separate node from targets
     * 4. Parse target nodes as a whitespace-separated list
     * 5. Build a map from node names to their sets of target nodes
     *
     * Example input:
     * you: svr fft
     * svr: dac out
     * fft: dac
     * dac: out
     *
     * Result: ReactorMap with connections: {you→{svr,fft}, svr→{dac,out}, fft→{dac}, dac→{out}}
     *
     * @param lines the raw input lines from the file
     * @return a ReactorMap containing the parsed directed graph structure
     */
    @Override
    public ReactorMap map(List<String> lines) {
        Map<String, Set<String>> connections =
                lines.stream()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty() && !line.startsWith("["))
                        .map(line -> line.split(":\\s*"))  // Split on colon with optional whitespace
                        .filter(parts -> parts.length >= 2)  // Ensure valid format (node: targets)
                        .collect(Collectors.toMap(
                                parts -> parts[0].trim(),  // Node name (map key)
                                parts -> Arrays.stream(parts[1].split("\\s+"))  // Split targets on whitespace
                                        .filter(s -> !s.isBlank())  // Remove empty strings
                                        .collect(Collectors.toSet())  // Collect as set of targets
                        ));

        return new ReactorMap(connections);
    }
}