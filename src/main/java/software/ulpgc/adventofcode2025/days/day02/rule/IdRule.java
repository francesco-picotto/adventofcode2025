package software.ulpgc.adventofcode2025.days.day02.rule;

/**
 * Strategy interface for validating and evaluating IDs based on specific criteria.
 *
 * Implementations of this interface define different validation rules for IDs,
 * such as checking for repetition patterns in the ID's digit sequence.
 */
public interface IdRule {
    /**
     * Evaluates whether an ID is valid according to the rule's criteria.
     *
     * If the ID satisfies the validation criteria, the method returns the ID itself.
     * If the ID does not meet the criteria, the method returns 0.
     *
     * @param id The ID to evaluate
     * @return The ID value if valid, 0 otherwise
     */
    long evaluate(long id);
}