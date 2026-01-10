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

        long splitCount = 0;

        for(int r = 0; r < manifold.getHeight(); r++){
            Set<Integer> nextRowBeams = new HashSet<>();
            for (int c : activeBeams){
                char current = manifold.getAt(r, c);
                if(current == '^'){
                    splitCount++;
                    nextRowBeams.add(c - 1);
                    nextRowBeams.add(c + 1);
                }else nextRowBeams.add(c);
            }
            activeBeams = nextRowBeams;
        }
        return splitCount;

    }
}
