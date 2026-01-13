package software.ulpgc.adventofcode2025.days.day01.domain;

import software.ulpgc.adventofcode2025.days.day01.strategy.PasswordStrategy;

public class Dial {
    private int position = 50;
    public static final int SIZE = 100;

    /**
     * Rotates the dial according to the given instruction and counts zeros using the provided strategy.
     *
     * Parses the instruction to extract the direction (first character) and number of steps
     * (remaining characters), then delegates zero counting to the strategy before updating
     * the dial's position.
     *
     * @param instruction A string containing direction ('L' or 'R') followed by step count (e.g., "R25")
     * @param strategy The strategy used to count how many times position zero is encountered
     * @return The number of times position zero was encountered during this rotation
     */
    public int rotate(String instruction, PasswordStrategy strategy) {
        char dir = instruction.charAt(0);
        int steps = Integer.parseInt(instruction.substring(1));
        int zerosFound = strategy.countZeros(this.position, steps, dir);
        this.position = calculateNewPosition(this.position, steps, dir);
        return zerosFound;
    }

    /**
     * Calculates the new position after rotating the dial by the specified number of steps.
     *
     * The dial wraps around at position 100, so positions are always in the range [0, 99].
     * Left rotations decrease the position, while right rotations increase it.
     * The modulo operation with double adjustment ensures proper wrapping for negative values.
     *
     * @param current The current position on the dial (0-99)
     * @param steps The number of positions to rotate
     * @param dir The direction of rotation ('L' for left/counter-clockwise, 'R' for right/clockwise)
     * @return The new position after rotation, guaranteed to be in the range [0, 99]
     */
    private int calculateNewPosition(int current, int steps, char dir) {
        int delta = (dir == 'L') ? -steps : steps;
        return ((current + delta) % SIZE + SIZE) % SIZE;
    }
}