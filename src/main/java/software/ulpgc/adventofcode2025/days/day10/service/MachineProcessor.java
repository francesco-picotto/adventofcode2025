package software.ulpgc.adventofcode2025.days.day10.service;

import software.ulpgc.adventofcode2025.days.day10.domain.Machine;
import software.ulpgc.adventofcode2025.days.day10.solver.MachineSolver;

import java.util.List;

/**
 * Service class that processes multiple machine puzzles using a specified solving strategy.
 * This class follows the Strategy pattern, delegating the actual solving logic to a
 * MachineSolver implementation and aggregating the results across all machines.
 */
public class MachineProcessor {
    private final MachineSolver solver;

    /**
     * Constructs a MachineProcessor with a specific solving strategy.
     *
     * @param solver the MachineSolver implementation to use for solving each machine
     */
    public MachineProcessor(MachineSolver solver) {
        this.solver = solver;
    }

    /**
     * Processes a list of machines and returns the total number of button presses required.
     * This method solves each machine independently using the configured solver and sums
     * all the individual results.
     *
     * @param machines the list of machines to process
     * @return the sum of button presses required to solve all machines
     */
    public long solve(List<Machine> machines) {
        return machines.stream()
                .mapToLong(solver::solve)
                .sum();
    }
}