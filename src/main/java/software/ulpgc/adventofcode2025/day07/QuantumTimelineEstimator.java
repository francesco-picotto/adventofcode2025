package software.ulpgc.adventofcode2025.day07;
import java.util.Arrays;
import java.util.List;

public class QuantumTimelineEstimator implements ManifoldAnalyzer{
    @Override
    public long analyze(List<String> grid){

        TachyonManifold manifold = new TachyonManifold(grid);
        int width = grid.get(0).length();


        long[] currentTimelines = new long[width];
        currentTimelines[manifold.getStartColumn()] = 1;

        for (int r=0; r< manifold.getHeight(); r++){
            long[] nextTimelines = new long[width];

            for(int c =0; c<width; c++){
                if(currentTimelines[c] == 0) continue;

                char current = manifold.getAt(r, c);
                if(current == '^'){
                    if (c - 1 >= 0) nextTimelines[c - 1] += currentTimelines[c];
                    if (c + 1 < width) nextTimelines[c + 1] += currentTimelines[c];
                }else nextTimelines[c] += currentTimelines[c];
            }
            currentTimelines = nextTimelines;
        }
        return Arrays.stream(currentTimelines).sum();

    }

}
