package software.ulpgc.adventofcode2025.days.day06;
import software.ulpgc.adventofcode2025.core.InputProvider;


public class Main {
    public static void main(String[] args) {

        var input = new InputProvider("src/main/resources/inputs").provide("day06.txt", lines -> lines);
        System.out.println("result1: " + new MathWorksheetProcessor(new StandardColumnAnalyzer()).solve(input));
        System.out.println("result2: " + new MathWorksheetProcessor(new ReverseVerticalAnalyzer()).solve(input));
    }

}
