package software.ulpgc.adventofcode2025.days.day07.analyzer;
import software.ulpgc.adventofcode2025.days.day07.domain.TachyonManifold;

import java.util.Arrays;
import java.util.List;

/**
 * Analyzer that calculates the total number of quantum timelines created by beam splitting.
 *
 * This analyzer tracks not just where beams are, but how many distinct timeline paths
 * exist at each position. When a beam splits, it doubles the number of timelines passing
 * through the resulting beam positions. This creates exponential growth in timeline count
 * as beams encounter multiple splitters. The analyzer uses dynamic programming to
 * efficiently track timeline counts across all columns.
 */
public class QuantumTimelineEstimator implements ManifoldAnalyzer {
    /**
     * Analyzes the manifold to calculate the total number of quantum timelines.
     *
     * Simulates beam propagation through the manifold using timeline counting:
     * 1. Starts with 1 timeline at the starting column
     * 2. For each row, calculates how timelines propagate to the next row
     * 3. When a beam splits, the timeline count is distributed to both resulting beams
     * 4. Returns the sum of all timeline counts at the final row
     *
     * Example simulation:
     * Row 0: 1 timeline at column 2, encounters splitter
     * Row 1: 1 timeline at column 1, 1 timeline at column 3
     * Row 2: Column 1 encounters splitter
     * Row 3: 1 timeline at column 0, 1 timeline at column 2, 1 timeline at column 3
     * Total: 3 timelines
     *
     * The key insight is that each split doubles the number of possible paths through
     * the manifold, and we track these multiplicatively across all beam positions.
     *
     * @param grid List of strings representing the manifold grid
     * @return The total number of distinct quantum timelines created
     */
    @Override
    public long analyze(List<String> grid){
        TachyonManifold manifold = new TachyonManifold(grid);
        long[] currentTimelines = new long[manifold.getWidth()];
        currentTimelines[manifold.getStartColumn()] = 1;
        int width = grid.get(0).length();

        for(int r = 0; r < manifold.getHeight(); r++){
            currentTimelines = calculateNextRow(manifold, r, currentTimelines);
        }
        return Arrays.stream(currentTimelines).sum();

    }

    /**
     * Calculates the timeline distribution for the next row based on the current row.
     *
     * For each column in the current row with timelines:
     * - If the position contains a splitter: distributes the timeline count equally
     *   to columns (current-1) and (current+1) in the next row
     * - If the position is empty: propagates all timelines straight down to the
     *   same column in the next row
     *
     * When multiple beams converge on the same column, their timeline counts are
     * added together, representing the sum of all possible paths that lead to
     * that position.
     *
     * Example:
     * Current row: [0, 2, 0, 4, 0] (2 timelines at col 1, 4 at col 3)
     * If col 1 has splitter, col 3 doesn't:
     * Next row: [2, 0, 2, 4, 0] (col 1's timelines split to 0 and 2, col 3 goes straight)
     *
     * @param manifold The tachyon manifold being analyzed
     * @param row The current row being processed
     * @param current Array of timeline counts for each column in the current row
     * @return Array of timeline counts for each column in the next row
     */
    private long[] calculateNextRow(TachyonManifold manifold, int row, long[] current) {
        long[] next = new long[manifold.getWidth()];
        for (int c = 0; c < manifold.getWidth(); c++) {
            if (current[c] == 0) continue;

            if (manifold.isSplitter(row, c)) {
                if (c - 1 >= 0) next[c - 1] += current[c];
                if (c + 1 < manifold.getWidth()) next[c + 1] += current[c];
            } else {
                next[c] += current[c];
            }
        }
        return next;
    }

}