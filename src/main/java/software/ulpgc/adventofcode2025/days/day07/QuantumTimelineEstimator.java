package software.ulpgc.adventofcode2025.days.day07;
import java.util.Arrays;
import java.util.List;

public class QuantumTimelineEstimator implements ManifoldAnalyzer{
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
