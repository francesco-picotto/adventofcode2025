package software.ulpgc.adventofcode2025.days.day11;

public class ReactorProcessor {
    private final ReactorSolver solver;

    public ReactorProcessor(ReactorSolver solver) {
        this.solver = solver;
    }

    public long solve(ReactorMap map) {
        return solver.solve(map);
    }
}