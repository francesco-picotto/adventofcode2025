package software.ulpgc.adventofcode2025.days.day01;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day01.service.PasswordProcessor;
import software.ulpgc.adventofcode2025.days.day01.strategy.AdvancedStrategy;
import software.ulpgc.adventofcode2025.days.day01.strategy.BasicStrategy;


public class Main {
    /**
     * Entry point of the application that solves both parts of the password dial puzzle.
     *
     * Reads the input file containing rotation instructions, then processes them using
     * two different strategies: BasicStrategy (counts only final position) and
     * AdvancedStrategy (counts all intermediate positions).
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        var input = new InputProvider("src/main/resources/inputs").provide("input_day01.txt", lines -> lines);
        System.out.println("Part one result: " + new PasswordProcessor(new BasicStrategy()).solve(input));
        System.out.println("Part two result: " + new PasswordProcessor(new AdvancedStrategy()).solve(input));
    }
}
