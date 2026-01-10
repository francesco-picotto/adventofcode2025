package software.ulpgc.adventofcode2025.day05;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TotalFreshCapacityEstimator implements InventoryAnalyzer {
    @Override
    public long analyze(List<String> freshRanges, List<String> availableIds) {
        List<IngredientRange> ranges = parseAndSortRanges(freshRanges);
        if(ranges.isEmpty()) return 0;

        List<IngredientRange> merged = new ArrayList<>();
        IngredientRange current = ranges.get(0);

        for(int i = 1; i < ranges.size(); i++){
            IngredientRange next = ranges.get(i);

            if(next.getStart() <= current.getEnd() + 1){
                current.setEnd(Math.max(current.getEnd(), next.getEnd()));
            }
            else{
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        return merged.stream().mapToLong(IngredientRange::size).sum();

    }

    private List<IngredientRange> parseAndSortRanges(List<String> rawRanges) {
        List<IngredientRange> list = new ArrayList<>();
        for (String s : rawRanges){
            String[] p = s.split("-");
            list.add(new IngredientRange(Long.parseLong(p[0]),  Long.parseLong(p[1])));
        }
        Collections.sort(list);
        return list;
    }
}
