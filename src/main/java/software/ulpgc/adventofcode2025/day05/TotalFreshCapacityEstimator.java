package software.ulpgc.adventofcode2025.day05;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TotalFreshCapacityEstimator implements InventoryAnalyzer {
    @Override
    public long analyze(List<String> freshRanges, List<String> availableIds) {

        List<IngredientRange> sortedRanges = freshRanges.stream()
                .map(IngredientRange::parse)
                .sorted()
                .toList();

        return mergeRanges(sortedRanges).stream()
                .mapToLong(IngredientRange::size)
                .sum();
    }

    private List<IngredientRange> mergeRanges(List<IngredientRange> ranges) {
        if(ranges.isEmpty()) return Collections.emptyList();

        List<IngredientRange> merged = new ArrayList<>();
        IngredientRange current = ranges.get(0);

        for(int i = 1; i < ranges.size(); i++){
            IngredientRange next = ranges.get(i);
            if(next.start() <= current.end() + 1){
                current = new IngredientRange(current.start(), Math.max(current.end(), next.end()));
            }
            else{
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        return merged;
    }

}
