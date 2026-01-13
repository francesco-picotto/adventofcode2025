package software.ulpgc.adventofcode2025.days.day07.analyzer;

import software.ulpgc.adventofcode2025.days.day07.domain.TachyonManifold;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Analyzer that counts the total number of beam splits occurring in the manifold.
 *
 * This analyzer tracks which columns have active beams at each row and counts
 * how many times beams encounter splitters. Each split event is counted once,
 * regardless of how many resulting beams it creates. The analyzer simulates
 * beam propagation row by row, updating the set of active beam columns as
 * splits occur.
 */
public class BeamSplitCounter implements ManifoldAnalyzer {
    /**
     * Analyzes the manifold to count the total number of beam split events.
     *
     * Simulates beam propagation through the manifold row by row:
     * 1. Starts with a single beam at the starting column
     * 2. For each row, counts how many active beams encounter splitters
     * 3. Updates the set of active beams for the next row based on splits
     * 4. Returns the total count of split events
     *
     * Example simulation:
     * Row 0: Beam at column 2, encounters splitter → 1 split
     * Row 1: Beams at columns 1 and 3, column 1 has splitter → 1 split
     * Row 2: Beams at columns 0, 2, and 3 → 0 splits
     * Total: 2 splits
     *
     * @param grid List of strings representing the manifold grid
     * @return The total number of beam split events that occurred
     */
    @Override
    public long analyze(List<String> grid){
        TachyonManifold manifold = new TachyonManifold(grid);
        Set<Integer> activeBeams = new HashSet<>();
        activeBeams.add(manifold.getStartColumn());

        long totalSplits = 0;

        for (int r = 0; r < manifold.getHeight(); r++) {
            totalSplits += countSplitsInRow(manifold, r, activeBeams);
            activeBeams = calculateNextRowBeams(manifold, r, activeBeams);
        }

        return totalSplits;

    }

    /**
     * Counts how many beam splits occur in a specific row.
     *
     * Examines each active beam column and checks if it contains a splitter.
     * Each active beam that encounters a splitter counts as one split event.
     *
     * @param manifold The tachyon manifold being analyzed
     * @param row The current row being examined
     * @param activeBeams Set of column indices where beams are active in this row
     * @return The number of splits that occur in this row
     */
    private long countSplitsInRow(TachyonManifold manifold, int row, Set<Integer> activeBeams) {
        return activeBeams.stream()
                .filter(col -> manifold.isSplitter(row, col))
                .count();
    }

    /**
     * Calculates which columns will have active beams in the next row.
     *
     * For each active beam in the current row:
     * - If it encounters a splitter: creates two new beams in the next row
     *   at columns (current-1) and (current+1), respecting grid boundaries
     * - If it doesn't encounter a splitter: continues straight to the same
     *   column in the next row
     *
     * Multiple beams can converge on the same column, but the set ensures
     * each column is only represented once regardless of how many beams
     * arrive there.
     *
     * @param manifold The tachyon manifold being analyzed
     * @param row The current row being processed
     * @param currentBeams Set of column indices where beams are active in this row
     * @return Set of column indices where beams will be active in the next row
     */
    private Set<Integer> calculateNextRowBeams(TachyonManifold manifold, int row, Set<Integer> currentBeams) {
        Set<Integer> nextBeams = new HashSet<>();

        for (int col : currentBeams) {
            if (manifold.isSplitter(row, col)) {
                if (col - 1 >= 0) nextBeams.add(col - 1);
                if (col + 1 < manifold.getWidth()) nextBeams.add(col + 1);
            } else {
                nextBeams.add(col);
            }
        }

        return nextBeams;
    }
}