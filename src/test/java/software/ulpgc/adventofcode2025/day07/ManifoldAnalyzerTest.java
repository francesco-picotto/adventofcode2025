package software.ulpgc.adventofcode2025.day07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day07.BeamSplitCounter;
import software.ulpgc.adventofcode2025.days.day07.ManifoldAnalyzer;
import software.ulpgc.adventofcode2025.days.day07.QuantumTimelineEstimator;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for ManifoldAnalyzer implementations.
 * Verifies the logic for counting beam splits and estimating quantum timelines.
 */
public class ManifoldAnalyzerTest {

    @Test
    @DisplayName("Should return 0 splits and 1 timeline for a straight path without splitters")
    public void testWithSimplePath() {
        // 3x3 Grid: The beam starts at (0,1) and goes straight down without encountering '^'
        List<String> grid = List.of(
                ".S.",
                "...",
                "..."
        );

        ManifoldAnalyzer counter = new BeamSplitCounter();
        ManifoldAnalyzer estimator = new QuantumTimelineEstimator();

        // BeamSplitCounter: 0 splitters encountered
        assertEquals(0, counter.analyze(grid));

        // QuantumTimelineEstimator: A single timeline reaches the end
        assertEquals(1, estimator.analyze(grid));
    }

    @Test
    @DisplayName("Should count 1 split and double timelines when a single splitter is hit")
    public void testWithSingleSplitter() {
        // 3x3 Grid: The beam hits a splitter at (1,1)
        List<String> grid = List.of(
                ".S.",
                ".^.",
                "..."
        );

        ManifoldAnalyzer counter = new BeamSplitCounter();
        ManifoldAnalyzer estimator = new QuantumTimelineEstimator();

        // BeamSplitCounter: Hits the '^' character once
        assertEquals(1, counter.analyze(grid));

        // QuantumTimelineEstimator: The splitter doubles the timelines (one left, one right)
        // Row 0: [0, 1, 0]
        // Row 1: '^' at col 1 -> distributes to col 0 and col 2
        // Row 2: [1, 0, 1] -> Total Sum = 2
        assertEquals(2, estimator.analyze(grid));
    }

    @Test
    @DisplayName("Should handle splitters at the grid boundaries correctly")
    public void testWithBoundarySplitter() {
        // Grid where the splitter is on the left boundary
        List<String> grid = List.of(
                "S..",
                "^..",
                "..."
        );

        ManifoldAnalyzer counter = new BeamSplitCounter();
        ManifoldAnalyzer estimator = new QuantumTimelineEstimator();

        // One split is still registered
        assertEquals(1, counter.analyze(grid));

        // QuantumTimelineEstimator: Splitter at (1,0) tries to send to -1 (discarded) and +1
        // Row 1 result: timelines[1] = 1. Total Sum = 1
        assertEquals(1, estimator.analyze(grid));
    }

    @Test
    @DisplayName("Should correctly calculate results for multiple splitters and overlapping paths")
    public void testWithComplexSplits() {
        // Grid with multiple levels of splitting
        List<String> grid = List.of(
                ".S.",  // R0: 1 beam at col 1
                ".^.",  // R1: 1 beam hits ^ -> splits into col 0 and 2. Split count = 1
                "^.^"   // R2: Beams at 0 and 2 both hit ^. Split count = 1 + 2 = 3
        );

        ManifoldAnalyzer counter = new BeamSplitCounter();
        ManifoldAnalyzer estimator = new QuantumTimelineEstimator();

        // BeamSplitCounter: 1 split at row 1, then 2 beams hit splits at row 2. Total = 3
        assertEquals(3, counter.analyze(grid));

        // QuantumTimelineEstimator:
        // R0: [0, 1, 0]
        // R1: [1, 0, 1] (after splitting col 1)
        // R2: Col 0 sends to 1. Col 2 sends to 1. Resulting in R3: [0, 2, 0]
        assertEquals(2, estimator.analyze(grid));
    }
}