package software.ulpgc.adventofcode2025.days.day01.service;

import software.ulpgc.adventofcode2025.days.day01.domain.Dial;
import software.ulpgc.adventofcode2025.days.day01.strategy.PasswordStrategy;

import java.util.List;

public class PasswordProcessor {
    private final PasswordStrategy strategy;

    /**
     * Constructs a PasswordProcessor with the specified counting strategy.
     *
     * @param strategy The strategy to use for counting zeros during dial rotation
     */
    public PasswordProcessor(PasswordStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Processes a list of rotation instructions and calculates the total number of times
     * the dial passes through or lands on position zero.
     *
     * Each instruction consists of a direction ('L' or 'R') followed by the number of steps.
     * The method creates a dial starting at position 50 and rotates it according to each
     * instruction, accumulating the count of zero crossings based on the configured strategy.
     *
     * @param instructions A list of rotation instructions (e.g., "R25", "L10")
     * @return The total number of times position zero was encountered
     */
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