package software.ulpgc.adventofcode2025.days.day01;

import java.util.List;

public class PasswordProcessor {
    private final PasswordStrategy strategy;

    public PasswordProcessor(PasswordStrategy strategy) {
        this.strategy = strategy;
    }

    public long solve(List<String> instructions) {
        Dial dial = new Dial();
        long totalZeros = 0;

        for (String instruction : instructions) {
            if (instruction == null || instruction.isBlank()) continue;
            totalZeros += dial.rotate(instruction, strategy);
        }

        return totalZeros;
    }
}
