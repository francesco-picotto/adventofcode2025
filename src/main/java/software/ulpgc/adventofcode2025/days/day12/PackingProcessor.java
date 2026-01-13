package software.ulpgc.adventofcode2025.days.day12;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PackingProcessor {
    private final FittingStrategy strategy;

    public PackingProcessor(FittingStrategy strategy) {
        this.strategy = strategy;
    }

    public long solve(PuzzleData data) {
        return data.regions().parallelStream()
                .filter(region -> {
                    List<Shape> required = flattenShapes(data.shapes(), region.counts());

                    // Euristica di Pruning (Area Check)
                    int totalPoints = required.stream().mapToInt(s -> s.points().size()).sum();
                    if (totalPoints > (region.w() * region.h())) return false;

                    return strategy.canFit(region.w(), region.h(), required);
                })
                .count();
    }

    private List<Shape> flattenShapes(List<Shape> shapes, List<Integer> counts) {
        return IntStream.range(0, counts.size())
                .boxed()
                .flatMap(i -> Collections.nCopies(counts.get(i), shapes.get(i)).stream())
                .toList();
    }
}