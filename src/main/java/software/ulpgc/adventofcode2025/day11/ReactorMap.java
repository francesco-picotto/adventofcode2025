package software.ulpgc.adventofcode2025.day11;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public record ReactorMap(Map<String, Set<String>> connections) {
    public Set<String> getNeighbours(String node){
        return connections.getOrDefault(node, Collections.emptySet());
    }

    public long countPaths(String start, String end, Map<String, Long> memo){
        if(start.equals(end)) return 1L;
        if(memo.containsKey(start)) return memo.get(start);

        long paths = getNeighbours(start).stream()
                .mapToLong(n -> countPaths(n, end, memo))
                .sum();

        memo.put(start, paths);
        return paths;
    }
}
