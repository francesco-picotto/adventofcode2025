package software.ulpgc.adventofcode2025.days.day01;

public class BasicStrategy implements PasswordStrategy {
    @Override
    public int countZeros(int currentPosition, int steps, char direction) {
        int delta = (direction == 'L') ? -steps : steps;
        int finalPos = ((currentPosition + delta) % Dial.SIZE + Dial.SIZE) % Dial.SIZE;
        return isOnZero(finalPos);
    }

    private int isOnZero(int finalPos) {
        return (finalPos == 0) ? 1 : 0;
    }


}
