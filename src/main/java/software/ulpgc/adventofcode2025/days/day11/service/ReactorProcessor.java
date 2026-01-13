package software.ulpgc.adventofcode2025.days.day11.service;

import software.ulpgc.adventofcode2025.days.day11.domain.ReactorMap;
import software.ulpgc.adventofcode2025.days.day11.solver.ReactorSolver;

/**
 * Service class that processes reactor maps using a specified solving strategy.
 * This class follows the Strategy pattern, delegating the actual solving logic
 * to a ReactorSolver implementation.
 */
public class ReactorProcessor {
    private final ReactorSolver solver;

    /**
     * Constructs a ReactorProcessor with a specific solving strategy.
     *
     * @param solver the ReactorSolver implementation to use for map analysis
     */
    public ReactorProcessor(ReactorSolver solver) {
        this.solver = solver;
    }

    /**
     * Processes a reactor map using the configured solver and returns the result.
     * This method delegates to the solver's solve method.
     *
     * @param map the reactor map to process
     * @return the numeric result of the analysis
     */
    public long solve(ReactorMap map) {
        return solver.solve(map);
    }
}