package software.ulpgc.adventofcode2025.day11;
import java.util.*;

public class SimplePathSolver implements ReactorSolver {
    private final Map<String, Long> memo = new HashMap<>();
    @Override
    public long solve(ReactorMap map) {
        memo.clear();
        return map.countPaths("you", "out", new HashMap<>());
    }
}
