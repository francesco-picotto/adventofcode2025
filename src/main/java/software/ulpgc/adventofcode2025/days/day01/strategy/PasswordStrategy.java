package software.ulpgc.adventofcode2025.days.day01.strategy;

/**
 * Strategy interface for counting zero positions during dial rotation.
 *
 * Implementations of this interface define different approaches to counting
 * how many times position zero is encountered when rotating a dial.
 */
public interface PasswordStrategy{
    /**
     * Counts the number of times position zero is encountered during a rotation.
     *
     * Different implementations may count only the final position (BasicStrategy)
     * or all intermediate positions (AdvancedStrategy) during the rotation.
     *
     * @param currentPosition The starting position on the dial (0-99)
     * @param steps The number of positions to rotate
     * @param direction The direction of rotation ('L' for left, 'R' for right)
     * @return The count of times position zero was encountered
     */
    long countZeros(int currentPosition, int steps, char direction);
}