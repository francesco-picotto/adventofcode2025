package software.ulpgc.adventofcode2025.day05;

import java.util.List;

public class StockFreshnessChecker implements InventoryAnalyzer {
    @Override
    public long analyze(List<String> freshRanges, List<String> availableIds) {
        return availableIds.stream()
                .mapToLong(Long::parseLong)
                .filter(id->isIdWithinAnyRange(id, freshRanges))
                .count();
    }

    private boolean isIdWithinAnyRange(long id, List<String> ranges) {
        for(String range : ranges){
            String[] parts = range.split("-");
            long low = Long.parseLong(parts[0]);
            long high = Long.parseLong(parts[1]);
            if(id >= low && id <= high) return true;
        }
        return false;
    }


}
