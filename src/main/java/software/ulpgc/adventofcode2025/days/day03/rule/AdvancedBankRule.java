package software.ulpgc.adventofcode2025.days.day03.rule;

/**
 * Advanced extraction rule that extracts a 12-digit value from a bank code string.
 *
 * This rule iteratively selects 12 digits by finding maximum characters in
 * progressively constrained search windows. Each subsequent digit is selected
 * from a range that starts after the previous digit and ends at a position that
 * ensures enough remaining characters for the digits still to be selected.
 */
public class AdvancedBankRule implements BankRule {
    /**
     * The number of digits to extract from each bank code.
     */
    private static final int J_LENGTH = 12;


    /**
     * Extracts a 12-digit numerical value from the bank code string.
     *
     * The extraction algorithm uses a sliding window approach:
     * - For each of the 12 positions (k = 0 to 11):
     *   - Start searching after the position of the previous selected digit
     *   - End searching at a position that leaves exactly (12-k) characters remaining
     *   - Select the maximum character in this constrained range
     *
     * This ensures that:
     * 1. Selected digits appear in increasing index order in the original string
     * 2. There are always enough remaining characters for subsequent selections
     * 3. Each position maximizes the digit value within its constraints
     *
     * Example: For a bank code "98765432109876543210", the algorithm finds the
     * maximum digit in progressively narrowing windows to extract 12 digits.
     *
     * @param bank The bank code string to process (must have at least 12 characters)
     * @return A 12-digit numerical value extracted from the bank code
     */
    @Override
    public long evaluate (String bank) {
        int len = bank.length();
        StringBuilder joltagestr = new StringBuilder();
        int currentLastIdx = -1;

        for (int k = 0; k < J_LENGTH; k++) {
            int start = currentLastIdx + 1;
            int end = len - J_LENGTH + k;

            currentLastIdx = BankRule.findMaxIndex(bank, start, end);
            joltagestr.append(bank.charAt(currentLastIdx));
        }
        return Long.parseLong(joltagestr.toString());
    }
}