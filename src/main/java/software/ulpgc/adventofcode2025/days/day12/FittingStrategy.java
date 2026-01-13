package software.ulpgc.adventofcode2025.days.day12;

import java.util.List;
import java.util.Set;

public interface FittingStrategy {
    // La firma deve usare List<Set<Shape>>
    boolean canFit(int width, int height, List<Set<Shape>> variations);
}