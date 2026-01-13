package software.ulpgc.adventofcode2025.days.day02;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day02.rule.MultipleRepeatRule;
import software.ulpgc.adventofcode2025.days.day02.rule.SimpleRepeatRule;
import software.ulpgc.adventofcode2025.days.day02.service.IdProcessor;


public class Main {
    /**
     * Entry point of the application that validates IDs based on repetition patterns.
     *
     * Reads the input file containing ID ranges, then processes them using two different
     * validation rules: SimpleRepeatRule (checks for exact half-repetition) and
     * MultipleRepeatRule (checks for prime-divisor repetition patterns).
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        var input = new InputProvider("src/main/resources/inputs").provide("input_day02.txt", new Day02Mapper());
        System.out.println("Total part one: " + new IdProcessor(new SimpleRepeatRule()).solve(input));
        System.out.println("Total part two: " + new IdProcessor(new MultipleRepeatRule()).solve(input));
    }
}