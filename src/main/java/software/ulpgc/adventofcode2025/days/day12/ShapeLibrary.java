package software.ulpgc.adventofcode2025.days.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ShapeLibrary(List<Set<Shape>> allVariations) {
    public static Set<Shape> generateVariation(Shape base){
        Set<Shape> variants = new HashSet<>();
        Shape current = base;
        for(int i=0; i<4; i++){
            current = current.rotate();
            variants.add(current);
            variants.add(current.flip());
        }
        return variants;
    }
}
