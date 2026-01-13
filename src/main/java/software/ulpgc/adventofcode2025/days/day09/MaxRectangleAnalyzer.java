package software.ulpgc.adventofcode2025.days.day09;
import java.util.*;

public class MaxRectangleAnalyzer implements GridAnalyzer {
    @Override
    public long analyze(List<Tile> tiles) {
        long maxArea = 0;

        for(int i=0; i<tiles.size(); i++){
            for(int j= i + 1; j<tiles.size(); j++){
                Rectangle rect = Rectangle.from(tiles.get(i), tiles.get(j));
                maxArea = Math.max(maxArea, rect.area());
            }
        }
        return maxArea;
    }
}
