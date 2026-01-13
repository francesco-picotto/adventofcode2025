package software.ulpgc.adventofcode2025.days.day02.rule;

/**
 * Validation rule that checks if an ID consists of two identical halves.
 *
 * This rule validates IDs where the first half of the digits is exactly
 * repeated in the second half. For example, "123123" is valid because
 * "123" is repeated, while "123456" is not valid.
 */
public class SimpleRepeatRule implements IdRule {
    /**
     * Evaluates whether an ID has its first half repeated as the second half.
     *
     * The ID is considered valid if:
     * 1. It has an even number of digits
     * 2. The first half of digits exactly matches the second half
     *
     * For example:
     * - 123123 → valid (returns 123123)
     * - 7777 → valid (returns 7777)
     * - 12345 → invalid (odd length, returns 0)
     * - 123456 → invalid (halves don't match, returns 0)
     *
     * @param id The ID to evaluate
     * @return The ID value if it has repeated halves, 0 otherwise
     */
    @Override
    public long evaluate(long id) {
        String idstr = String.valueOf(id);
        if(idstr.length() % 2 == 0 && (idstr.substring(0, idstr.length()/2))
                .equals(idstr.substring(idstr.length()/2)))
            return Long.parseLong(idstr);
        else return 0;
    }
}