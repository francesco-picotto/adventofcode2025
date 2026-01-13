package software.ulpgc.adventofcode2025.days.day11.solver;

import software.ulpgc.adventofcode2025.days.day11.domain.ReactorMap;

import java.util.*;

/**
 * Solver implementation that counts all distinct paths from a starting point ("you")
 * to an exit point ("out") in the reactor network.
 *
 * This solver provides the simplest path-counting solution, finding all possible
 * routes through the reactor without additional constraints.
 */
public class SimplePathSolver implements ReactorSolver {
    private final Map<String, Long> memo = new HashMap<>();

    /**
     * Calculates the total number of distinct paths from the "you" node to the "out" node.
     *
     * This method uses the ReactorMap's path counting functionality with memoization
     * to efficiently compute the result. The memo cache is cleared before each solve
     * to ensure fresh computation for each new map.
     *
     * @param map the reactor map containing the network structure
     * @return the total number of distinct paths from "you" to "out"
     */
    @Override
    public long solve(ReactorMap map) {
        // Clear memoization cache to ensure fresh computation
        memo.clear();

        // Count all paths from starting node "you" to exit node "out"
        return map.countPaths("you", "out", new HashMap<>());
    }
}