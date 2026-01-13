package software.ulpgc.adventofcode2025.days.day11.solver;

import software.ulpgc.adventofcode2025.days.day11.domain.ReactorMap;

/**
 * Strategy interface for solving reactor path-finding puzzles.
 * Implementations of this interface provide different algorithms for analyzing
 * reactor maps and calculating path-related metrics.
 */
public interface ReactorSolver {

    /**
     * Solves a reactor puzzle and returns a numeric result based on the implementation's algorithm.
     * The specific meaning of the returned value depends on the concrete implementation,
     * but typically represents a count of paths or routing possibilities.
     *
     * @param map the reactor map containing the node network and connections
     * @return a long value representing the solution (interpretation varies by implementation)
     */
    long solve(ReactorMap map);
}