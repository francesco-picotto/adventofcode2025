package software.ulpgc.adventofcode2025.days.day02.domain;

import software.ulpgc.adventofcode2025.days.day02.rule.IdRule;

import java.util.stream.LongStream;

/**
 * Represents a range of IDs with inclusive start and end boundaries.
 *
 * This record provides functionality to parse range strings and evaluate
 * all IDs within the range against a validation rule.
 *
 * @param start The starting ID of the range (inclusive)
 * @param end The ending ID of the range (inclusive)
 */
public record IdRange(long start, long end) {

    /**
     * Factory method that parses a range string into an IdRange object.
     *
     * Parses strings in the format "start-end" (e.g., "100-200") where both
     * start and end are long integers. Whitespace around the numbers is trimmed.
     *
     * @param rangeStr A string representing the range in format "start-end"
     * @return An IdRange object with the parsed start and end values
     * @throws NumberFormatException if either part cannot be parsed as a long
     * @throws ArrayIndexOutOfBoundsException if the string doesn't contain exactly one hyphen
     */
    public static IdRange parse(String rangeStr) {
        String[] parts = rangeStr.split("-");
        return new IdRange(
                Long.parseLong(parts[0].trim()),
                Long.parseLong(parts[1].trim())
        );
    }

    /**
     * Evaluates all IDs in the range and returns the sum of valid IDs.
     *
     * Generates a stream of all IDs from start to end (inclusive), applies
     * the validation rule to each ID, and sums the results. Valid IDs return
     * their own value while invalid IDs return 0, so only valid IDs contribute
     * to the final sum.
     *
     * @param rule The validation rule to apply to each ID in the range
     * @return The sum of all valid IDs in the range
     */
    public long sumValidIds(IdRule rule) {
        return LongStream.rangeClosed(start, end)
                .map(rule::evaluate)
                .sum();
    }
}