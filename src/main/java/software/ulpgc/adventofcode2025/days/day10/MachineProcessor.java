package software.ulpgc.adventofcode2025.days.day10;

import java.util.List;

public class MachineProcessor {
    private final MachineSolver solver;

    public MachineProcessor(MachineSolver solver) {
        this.solver = solver;
    }

    public long solve(List<Machine> machines) {
        return machines.stream()
                .mapToLong(solver::solve)
                .sum();
    }
}