package software.ulpgc.adventofcode2025.day09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suite for GridAnalyzer implementations.
 * Focuses on verifying area calculation and boundary validation.
 */
public class GridAnalyzerTest {

    private List<Tile> simpleSquarePolygon;

    @BeforeEach
    void setUp() {
        // A simple 4x4 square polygon defined by its vertices (0,0) to (3,3)
        // Area = (3-0+1) * (3-0+1) = 16
        simpleSquarePolygon = List.of(
                new Tile(0, 0),
                new Tile(3, 0),
                new Tile(3, 3),
                new Tile(0, 3)
        );
    }

    @Test
    void testMaxRectangleAnalyzer_ShouldReturnMaxPossibleArea() {
        GridAnalyzer analyzer = new MaxRectangleAnalyzer();
        long result = analyzer.analyze(simpleSquarePolygon);

        // The brute force analyzer should find the 4x4 area between (0,0) and (3,3)
        assertEquals(16L, result, "MaxRectangleAnalyzer should calculate an area of 16 for a 4x4 square");
    }

    @Test
    void testLoopRectangleAnalyzer_ShouldReturnValidatedArea() {
        GridAnalyzer analyzer = new LoopRectangleAnalyzer();
        long result = analyzer.analyze(simpleSquarePolygon);

        // The loop analyzer should also find 16, as all points within the square are valid
        assertEquals(16L, result, "LoopRectangleAnalyzer should calculate an area of 16 for a 4x4 square");
    }

    @Test
    void testAnalyzers_WithEmptyList_ShouldReturnZero() {
        List<Tile> emptyList = List.of();

        assertEquals(0L, new MaxRectangleAnalyzer().analyze(emptyList));
        assertEquals(0L, new LoopRectangleAnalyzer().analyze(emptyList));
    }

    @Test
    void testLoopRectangleAnalyzer_WithConcavePolygon() {
        // Defining an L-shaped polygon to test the "isPointInside" logic
        // Vertices: (0,0), (2,0), (2,1), (1,1), (1,2), (0,2)
        List<Tile> lShape = List.of(
                new Tile(0, 0),
                new Tile(2, 0),
                new Tile(2, 1),
                new Tile(1, 1),
                new Tile(1, 2),
                new Tile(0, 2)
        );

        GridAnalyzer analyzer = new LoopRectangleAnalyzer();
        long result = analyzer.analyze(lShape);

        // The maximum valid rectangle in this L-shape is either the bottom 2x1 or side 1x2.
        // Area should be 2, not a 2x2 rectangle (area 4) which would be partially outside.
        assertEquals(2L, result, "LoopRectangleAnalyzer should respect polygon boundaries in concave shapes");
    }
}