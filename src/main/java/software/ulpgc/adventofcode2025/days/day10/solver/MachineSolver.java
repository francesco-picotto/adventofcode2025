package software.ulpgc.adventofcode2025.days.day10.solver;

import software.ulpgc.adventofcode2025.days.day10.domain.Machine;

/**
 * Strategy interface for solving machine puzzles.
 * Implementations of this interface provide different algorithms for determining
 * the minimum number of button presses required to achieve a machine's target state.
 */
public interface MachineSolver {

    /**
     * Solves a machine puzzle and returns the minimum number of button presses required.
     * The specific solving strategy depends on the concrete implementation.
     *
     * @param machine the machine configuration to solve
     * @return the minimum number of button presses needed to reach the target state,
     *         or 0 if the target cannot be reached
     */
    long solve(Machine machine);
}