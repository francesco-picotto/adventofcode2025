package software.ulpgc.adventofcode2025.days.day11;
import java.util.*;

public class MandatoryNodeSolver implements ReactorSolver {
    @Override
    public long solve(ReactorMap map) {
        long pathA = calculateChain(map, "svr", "fft", "dac", "out");
        long pathB = calculateChain(map, "svr", "dac", "fft", "out");
        
        return Math.max(pathA, pathB);
    }

    private long calculateChain(ReactorMap map, String ... nodes) {
        long total = 1L;
        for (int i=0; i<nodes.length - 1; i++){
            long segment = map.countPaths(nodes[i], nodes[i+1], new HashMap<>());
            if(segment == 0) return 0;
            total *= segment;
        }
        return total;
    }
}
