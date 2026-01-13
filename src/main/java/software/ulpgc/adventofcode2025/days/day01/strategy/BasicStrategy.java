package software.ulpgc.adventofcode2025.days.day01.strategy;

import software.ulpgc.adventofcode2025.days.day01.domain.Dial;

/**
 * Basic implementation of PasswordStrategy that only checks the final position.
 *
 * This strategy takes a simplified approach by calculating the final position directly
 * and checking only whether that final position is zero, ignoring any intermediate
 * positions the dial may pass through during rotation.
 */
public class BasicStrategy implements PasswordStrategy {
    /**
     * Counts whether the final position after rotation is zero.
     *
     * Calculates the ending position directly using modular arithmetic without
     * simulating intermediate steps. Returns 1 if the final position is zero,
     * and 0 otherwise. This provides a more efficient but less comprehensive
     * count compared to the AdvancedStrategy.
     *
     * @param currentPosition The starting position on the dial (0-99)
     * @param steps The number of positions to rotate
     * @param direction The direction of rotation ('L' for left, 'R' for right)
     * @return 1 if the final position is zero, 0 otherwise
     */
    @Override
    public int countZeros(int currentPosition, int steps, char direction) {
        int delta = (direction == 'L') ? -steps : steps;
        int finalPos = ((currentPosition + delta) % Dial.SIZE + Dial.SIZE) % Dial.SIZE;
        return isOnZero(finalPos);
    }

    /**
     * Checks whether the given position is zero.
     *
     * @param finalPos The position to check
     * @return 1 if the position is zero, 0 otherwise
     */
    private int isOnZero(int finalPos) {
        return (finalPos == 0) ? 1 : 0;
    }


}
