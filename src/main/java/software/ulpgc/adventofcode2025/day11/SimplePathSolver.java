package software.ulpgc.adventofcode2025.day11;
import java.util.*;

public class SimplePathSolver implements ReactorSolver {
    private final Map<String, Long> memo = new HashMap<>();
    @Override
    public long solve(ReactorMap map) {
        memo.clear();
        return countPaths(map, "you", "out");
    }

    private long countPaths(ReactorMap map, String current, String end) {
        if(current.equals(end)) return 1L;
        if(memo.containsKey(current)) return memo.get(current);

        long paths = map.getNeighbours(current).stream()
                .mapToLong(neighbour -> countPaths(map, neighbour, end))
                .sum();

        memo.put(current, paths);
        return paths;
    }
}
