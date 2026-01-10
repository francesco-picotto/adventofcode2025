package software.ulpgc.adventofcode2025.day11;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public record ReactorMap(Map<String, Set<String>> connections) {
    public Set<String> getNeighbours(String node){
        return connections.getOrDefault(node, Collections.emptySet());
    }
}
