package software.ulpgc.adventofcode2025.day07;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeamSplitCounter implements ManifoldAnalyzer{
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

    private long countSplitsInRow(TachyonManifold manifold, int row, Set<Integer> activeBeams) {
        return activeBeams.stream()
                .filter(col -> manifold.isSplitter(row, col))
                .count();
    }

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
