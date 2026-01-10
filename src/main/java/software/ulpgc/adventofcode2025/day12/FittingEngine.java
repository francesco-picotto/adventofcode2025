package software.ulpgc.adventofcode2025.day12;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FittingEngine {
    private final int width;
    private final int height;

    public FittingEngine(int width, int height){
        this.width = width;
        this.height = height;
    }

    public boolean canFit(List<Shape> requiredShapes){
        return solve(new HashSet<>(), requiredShapes, 0);
    }

    private boolean solve(Set<Point> occupied, List<Shape> remaining, int index) {
        if(index == remaining.size()) return true;

        Shape baseShape = remaining.get(index);
        Set<Shape> variations = ShapeLibrary.generateVariation(baseShape);

        for(Shape variant : variations){
            for(int y = 0; y <= height - 3; y++){
                for(int x = 0; x <= width - 3; x++){
                    //creiamo variabili finali per lambda
                    final int currentX = x, currentY = y;


                    Set<Point> translated = variant.points().stream()
                            .map(p -> p.translate(currentX,currentY))
                            .collect(Collectors.toSet());

                    if (Collections.disjoint(occupied, translated)) {
                        occupied.addAll(translated);

                        if(solve(occupied, remaining, index +1)) return true;
                        occupied.removeAll(translated);
                    }
                }
            }
        }
        return false;
    }
}
