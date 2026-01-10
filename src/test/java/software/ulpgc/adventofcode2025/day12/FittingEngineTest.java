package software.ulpgc.adventofcode2025.day12;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the FittingEngine and Shape geometric logic.
 */
public class FittingEngineTest {

    @Test
    void shouldNormalizeShapeToOriginAfterTransformation() {
        // Create an L-shaped polyomino starting at (5,5)
        // In a clean architecture, transformations should always bring the shape back to (0,0)
        Shape shape = new Shape(Set.of(
                new Point(5, 5),
                new Point(5, 6),
                new Point(6, 6)
        )).normalize();

        // After normalization, the minimum coordinates must be (0,0)
        assertTrue(shape.points().contains(new Point(0, 0)), "Normalized shape should contain the origin (0,0)");
        assertEquals(3, shape.points().size(), "Point count must remain constant after normalization");
    }

    @Test
    void shouldSuccessfullyFitShapesInPerfectSquare() {
        // Setup a 2x2 region (Area = 4)
        FittingEngine engine = new FittingEngine(2, 2);

        // Required: Two 1x2 vertical rectangles (Total Area = 4)
        Shape verticalRect = new Shape(Set.of(new Point(0, 0), new Point(0, 1)));
        List<Shape> shapes = List.of(verticalRect, verticalRect);

        // The engine should find a way to place them side-by-side
        assertTrue(engine.canFit(shapes), "Engine should fit two 1x2 rectangles into a 2x2 square");
    }

    @Test
    void shouldFailWhenTotalShapeAreaExceedsRegionArea() {
        // Setup a 2x2 region (Area = 4)
        FittingEngine engine = new FittingEngine(2, 2);

        // Required: Three 1x2 rectangles (Total Area = 6)
        Shape rect = new Shape(Set.of(new Point(0, 0), new Point(0, 1)));
        List<Shape> shapes = List.of(rect, rect, rect);

        // Physically impossible to fit 6 units of area into 4 units of space
        assertFalse(engine.canFit(shapes), "Engine should reject requests where area exceeds capacity");
    }

    @Test
    void shouldUtilizeRotationsToSolvePuzzle() {
        // Setup a 3x2 region
        FittingEngine engine = new FittingEngine(3, 2);

        // Required: One 3x1 horizontal bar and one 1x2 vertical bar
        // The engine must rotate the 1x2 bar or find a specific placement to succeed
        Shape horizontalBar = new Shape(Set.of(new Point(0, 0), new Point(1, 0), new Point(2, 0)));
        Shape verticalBar = new Shape(Set.of(new Point(0, 0), new Point(0, 1)));

        assertTrue(engine.canFit(List.of(horizontalBar, verticalBar)),
                "Engine should successfully use shape variations to find a solution");
    }

    @Test
    void shouldBacktrackWhenInitialPlacementIsInvalid() {
        // Setup a small grid where the first 'greedy' placement might block the second piece
        FittingEngine engine = new FittingEngine(2, 2);

        // Piece 1: a single point
        // Piece 2: a 2x1 bar
        Shape dot = new Shape(Set.of(new Point(0,0)));
        Shape bar = new Shape(Set.of(new Point(0,0), new Point(1,0)));

        assertTrue(engine.canFit(List.of(dot, bar)),
                "Engine should backtrack if the first piece blocks the second");
    }
}