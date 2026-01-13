package software.ulpgc.adventofcode2025.days.day03.rule;

/**
 * Simple extraction rule that extracts a 2-digit value from a bank code string.
 *
 * This rule selects two digits by finding the maximum character in two
 * non-overlapping, constrained search ranges. The first digit is selected
 * from near the beginning of the string, and the second digit is selected
 * from the remaining portion after the first digit's position.
 */
public class SimpleBankRule implements BankRule {

    /**
     * Extracts a 2-digit numerical value from the bank code string.
     *
     * The extraction process works as follows:
     * 1. Finds the index of the maximum character in the range [0, length-2]
     * 2. Finds the index of the maximum character in the range [firstIdx+1, length-1]
     * 3. Combines the characters at these two indices to form a 2-digit number
     *
     * This ensures the two selected digits don't overlap and provides a simple
     * method for extracting a representative value from the bank code.
     *
     * Example: For bank code "582917", if max in [0,4] is '9' at index 3,
     * and max in [4,5] is '7' at index 5, the result would be 97.
     *
     * @param bank The bank code string to process
     * @return A 2-digit numerical value extracted from the bank code
     */
    @Override
    public long evaluate (String bank) {
        int len = bank.length();
        int firstIdx = BankRule.findMaxIndex(bank, 0, len-2);
        int secondIdx = BankRule.findMaxIndex(bank, firstIdx + 1, len - 1);

        return Long.parseLong("" + bank.charAt(firstIdx) + bank.charAt(secondIdx));
    }
}