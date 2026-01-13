package software.ulpgc.adventofcode2025.days.day03.rule;

/**
 * Strategy interface for extracting numerical values from bank code strings.
 *
 * Implementations of this interface define different algorithms for selecting
 * specific characters from a bank code string based on finding maximum values
 * within constrained search ranges.
 */
public interface BankRule {
    /**
     * Extracts a numerical value from a bank code string according to the rule's logic.
     *
     * Different implementations may extract different numbers of digits based on
     * their specific algorithms for finding maximum characters in constrained ranges.
     *
     * @param bank The bank code string to process
     * @return The extracted numerical value as a long integer
     */
    long evaluate (String bank);

    /**
     * Utility method that finds the index of the maximum character within a specified range.
     *
     * Scans the string from start index to end index (inclusive) and returns the index
     * of the character with the highest lexicographic/numeric value. When multiple
     * characters have the same maximum value, returns the index of the first occurrence.
     *
     * This is a reusable utility method available to all implementations of BankRule.
     *
     * @param s The string to search
     * @param start The starting index of the search range (inclusive)
     * @param end The ending index of the search range (inclusive)
     * @return The index of the maximum character in the specified range
     */
    static int findMaxIndex(String s, int start, int end) {
        int maxIdx = start;
        for (int i = start + 1; i <= end; i++) {
            if (s.charAt(i) > s.charAt(maxIdx)) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }
}