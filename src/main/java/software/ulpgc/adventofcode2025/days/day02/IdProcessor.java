package software.ulpgc.adventofcode2025.days.day02;

import java.util.List;

public class IdProcessor {
    private final IdRule rule;

    public IdProcessor(IdRule rule) {
        this.rule = rule;
    }

    public long solve(List<String> ranges) {
        return ranges.stream()
                .map(IdRange::parse)
                .mapToLong(range -> range.sumValidIds(rule))
                .sum();
    }
}