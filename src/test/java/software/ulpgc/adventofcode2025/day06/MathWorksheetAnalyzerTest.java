package software.ulpgc.adventofcode2025.day06;

import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day06.analyzer.ReverseVerticalAnalyzer;
import software.ulpgc.adventofcode2025.days.day06.analyzer.StandardColumnAnalyzer;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathWorksheetAnalyzerTest {

    @Test
    public void testStandardColumnAnalyzer() {
        // Input example with two problems in separate columns
        // Problem 1 (Left): 10 + 20 = 30
        // Problem 2 (Right): 5 * 4 = 20
        List<String> input = List.of(
                "10   5",
                "20   4",
                " +   *"
        );

        StandardColumnAnalyzer analyzer = new StandardColumnAnalyzer();
        long result = analyzer.analyze(input);

        // Expected calculation: (10 + 20) + (5 * 4) = 50
        assertEquals(50L, result);
    }

    @Test
    public void testReverseVerticalAnalyzer() {
        // Example for ReverseVertical: numbers are extracted from right to left across columns
        // Column Index 2 (Right): 8, 2 with '+' operator => 10
        // Column Index 0 (Left): 3, 3 with '*' operator => 9
        List<String> input = List.of(
                "3  8  ",
                "3  2  ",
                "* +  "
        );

        ReverseVerticalAnalyzer analyzer = new ReverseVerticalAnalyzer();
        long result = analyzer.analyze(input);

        // Expected calculation: (8 + 2) + (3 * 3) = 19
        assertEquals(19L, result);
    }

    @Test
    public void testSingleNumberProblem() {
        // Edge case: A column containing only one number and an operator
        List<String> input = List.of(
                "42",
                " +"
        );
        StandardColumnAnalyzer analyzer = new StandardColumnAnalyzer();

        // Expected: The sum of a single number 42 is 42
        assertEquals(42L, analyzer.analyze(input));
    }

    @Test
    public void testEmptyColumnSeparation() {
        // Verifies that columns are correctly separated by whitespace
        List<String> input = List.of(
                "1    2",
                "1    2",
                "+    +"
        );
        StandardColumnAnalyzer analyzer = new StandardColumnAnalyzer();

        // Expected: (1+1) + (2+2) = 6
        assertEquals(6L, analyzer.analyze(input));
    }
}