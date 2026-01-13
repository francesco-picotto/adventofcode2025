package software.ulpgc.adventofcode2025.days.day01;

public class AdvancedStrategy implements PasswordStrategy {
    @Override
    public int countZeros(int currentPosition, int steps, char direction) {
        int zeros = 0;
        int current = currentPosition;
        for (int i = 0; i < steps; i++) {
            current = moveOneStep(current, direction);
            if (current == 0) zeros++;
        }
        return zeros;
    }

    private int moveOneStep(int pos, char dir) {
        return (dir == 'R') ? (pos + 1) % Dial.SIZE : (pos - 1 + Dial.SIZE) % Dial.SIZE;
    }
}
