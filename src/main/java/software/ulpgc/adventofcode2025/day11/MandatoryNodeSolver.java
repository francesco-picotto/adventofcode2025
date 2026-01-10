package software.ulpgc.adventofcode2025.day11;
import java.util.*;

public class MandatoryNodeSolver implements ReactorSolver {
    @Override
    public long solve(ReactorMap map) {
        long pathA = calculateChain(map, "svr", "fft", "dac", "out");
        long pathB = calculateChain(map, "svr", "dac", "fft", "out");
        
        return Math.max(pathA, pathB);
    }

    private long calculateChain(ReactorMap map, String ... nodes) {
        long total = 1L;
        for (int i=0; i<nodes.length - 1; i++){
            long segment = countPaths(map, new HashMap<>(), nodes[i], nodes[i+1]);
            if(segment == 0) return 0;
            total *= segment;
        }
        return total;
    }

    private long countPaths(ReactorMap map, Map<String, Long> memo, String start, String end) {
        if(start.equals(end)) return 1L;
        if(memo.containsKey(start)) return memo.get(start);

        long paths = map.getNeighbours(start).stream()
                .mapToLong(neighbour -> countPaths(map, memo, neighbour, end))
                .sum();

        memo.put(start, paths);
        return paths;
    }
}
