package software.ulpgc.adventofcode2025.day01;

public class AdvancedStrategy implements PasswordStrategy {
    @Override
    public int countZeros(int currentPosition, int steps, char direction) {
        int zeros = 0;
        int tempPosition = currentPosition;

        for (int i = 0; i < steps; i++) {
            if (direction == 'R') {
                tempPosition = (tempPosition + 1) % 100;
            }else tempPosition = (tempPosition - 1) % 100;

            if(tempPosition == 0) zeros++;
        }


        return zeros;
    }
}
