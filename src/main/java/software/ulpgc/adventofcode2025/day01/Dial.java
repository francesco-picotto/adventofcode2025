package software.ulpgc.adventofcode2025.day01;

public class Dial {
    private int position = 50;
    public static final int SIZE = 100;

    public int rotate(String instruction, PasswordStrategy strategy) {
        char dir = instruction.charAt(0);
        int steps = Integer.parseInt(instruction.substring(1));

        int zerosFound = strategy.countZeros(this.position, steps, dir);

        int delta = (dir == 'L') ? -steps : steps;
        this.position = calculateNewPosition(this.position, steps, dir);

        return zerosFound;
    }

    private int calculateNewPosition(int current, int steps, char dir) {
        int delta = (dir == 'L') ? -steps : steps;
        return (current + delta % SIZE) % SIZE;
    }
}
