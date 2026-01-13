package software.ulpgc.adventofcode2025.days.day06;
import software.ulpgc.adventofcode2025.core.InputProvider;
import software.ulpgc.adventofcode2025.days.day06.analyzer.ReverseVerticalAnalyzer;
import software.ulpgc.adventofcode2025.days.day06.analyzer.StandardColumnAnalyzer;
import software.ulpgc.adventofcode2025.days.day06.service.MathWorksheetProcessor;


public class Main {
    /**
     * Entry point of the application that processes math worksheet problems.
     *
     * Reads the input file containing a visual representation of math problems
     * arranged in columns, then processes them using two different parsing strategies:
     * - StandardColumnAnalyzer: Parses problems from left to right in standard order
     * - ReverseVerticalAnalyzer: Parses problems from right to left with vertical digit extraction
     *
     * Each problem consists of numbers arranged vertically with an operator at the bottom,
     * and the analyzers calculate the results based on the specified operations.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        var input = new InputProvider("src/main/resources/inputs").provide("input_day06.txt", lines -> lines);
        System.out.println("result1: " + new MathWorksheetProcessor(new StandardColumnAnalyzer()).solve(input));
        System.out.println("result2: " + new MathWorksheetProcessor(new ReverseVerticalAnalyzer()).solve(input));
    }

}