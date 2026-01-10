package software.ulpgc.adventofcode2025.day09;
import java.util.*;

public class MaxRectangleAnalyzer implements GridAnalyzer {
    @Override
    public long analyze(List<Tile> tiles) {
        long maxArea = 0;

        for(int i=0; i<tiles.size(); i++){
            for(int j= i + 1; j<tiles.size(); j++){
                long currentArea = tiles.get(i).areaTo(tiles.get(j));
                if(currentArea > maxArea)
                        maxArea = currentArea;
            }
        }
        return maxArea;
    }
}
