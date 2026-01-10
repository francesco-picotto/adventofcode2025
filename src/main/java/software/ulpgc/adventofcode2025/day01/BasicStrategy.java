package software.ulpgc.adventofcode2025.day01;

public class BasicStrategy implements PasswordStrategy {
    @Override
    public int countZeros(int currentPosition, int steps, char direction) {
        int delta = (direction == 'L') ? -steps : steps;
        int finalPos = ((currentPosition + delta) % 100 + 100) % 100;
        return isOnZero(finalPos);
    }

    private int isOnZero(int finalPos) {
        return (finalPos == 0) ? 1 : 0;
    }
}
