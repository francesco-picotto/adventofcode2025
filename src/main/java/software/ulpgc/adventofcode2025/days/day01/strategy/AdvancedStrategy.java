package software.ulpgc.adventofcode2025.days.day01.strategy;

import software.ulpgc.adventofcode2025.days.day01.domain.Dial;

/**
 * Advanced implementation of PasswordStrategy that counts all zero crossings during rotation.
 *
 * This strategy simulates the dial rotation step-by-step and counts every time
 * the dial passes through position zero during the movement, not just the final position.
 */
public class AdvancedStrategy implements PasswordStrategy {
    /**
     * Counts all occurrences of position zero during step-by-step rotation.
     *
     * Simulates rotating the dial one step at a time for the specified number of steps,
     * checking after each individual step whether the dial is at position zero.
     * This provides a complete count of all zero crossings during the rotation.
     *
     * @param currentPosition The starting position on the dial (0-99)
     * @param steps The number of positions to rotate
     * @param direction The direction of rotation ('L' for left, 'R' for right)
     * @return The total number of times position zero was encountered during the rotation
     */
    @Override
    public long countZeros(int currentPosition, int steps, char direction) {
        long zeros = 0;
        int current = currentPosition;
        for (int i = 0; i < steps; i++) {
            current = moveOneStep(current, direction);
            if (current == 0) zeros++;
        }
        return zeros;
    }

    /**
     * Moves the dial position by exactly one step in the specified direction.
     *
     * Handles wrapping around the dial boundaries: moving right from position 99
     * wraps to 0, and moving left from position 0 wraps to 99.
     *
     * @param pos The current position on the dial (0-99)
     * @param dir The direction to move ('L' for left, 'R' for right)
     * @return The new position after moving one step, in the range [0, 99]
     */
    private int moveOneStep(int pos, char dir) {
        return (dir == 'R') ? (pos + 1) % Dial.SIZE : (pos - 1 + Dial.SIZE) % Dial.SIZE;
    }
}