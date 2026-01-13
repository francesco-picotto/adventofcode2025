package software.ulpgc.adventofcode2025.days.day02.service;

import software.ulpgc.adventofcode2025.days.day02.domain.IdRange;
import software.ulpgc.adventofcode2025.days.day02.rule.IdRule;

import java.util.List;

public class IdProcessor {
    private final IdRule rule;

    /**
     * Constructs an IdProcessor with the specified validation rule.
     *
     * @param rule The rule to use for validating and evaluating IDs
     */
    public IdProcessor(IdRule rule) {
        this.rule = rule;
    }

    /**
     * Processes a list of ID ranges and calculates the sum of all valid IDs.
     *
     * Each range string is parsed into an IdRange object, which then evaluates
     * each ID within that range using the configured rule. The rule returns the
     * ID value if it's valid, or 0 if it's invalid. All valid IDs are summed
     * across all ranges to produce the final result.
     *
     * @param ranges A list of range strings in the format "start-end" (e.g., "100-200")
     * @return The sum of all valid IDs across all ranges
     */
    public long solve(List<String> ranges) {
        return ranges.stream()
                .map(IdRange::parse)
                .mapToLong(range -> range.sumValidIds(rule))
                .sum();
    }
}