package software.ulpgc.adventofcode2025.days.day05;
import java.util.List;

public class StockFreshnessChecker implements InventoryAnalyzer {
    @Override
    public long analyze(List<String> freshRanges, List<String> availableIds) {

        List<IngredientRange> ranges = freshRanges.stream()
                .map(IngredientRange::parse)
                .toList();


        return availableIds.stream()
                .mapToLong(Long::parseLong)
                .filter(id->ranges.stream().anyMatch(r->r.contains(id)))
                .count();
    }

}
