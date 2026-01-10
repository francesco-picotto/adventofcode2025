package software.ulpgc.adventofcode2025.day01;

public class Dial {
    private int position = 50;

    public int rotate(String instruction, PasswordStrategy strategy) {
        char dir = instruction.charAt(0);
        int steps = Integer.parseInt(instruction.substring(1));

        int zerosFound = strategy.countZeros(this.position, steps, dir);

        int delta = (dir == 'L') ? -steps : steps;
        this.position = ((this.position + delta) % 100 + 100) % 100;

        return zerosFound;
    }
}
