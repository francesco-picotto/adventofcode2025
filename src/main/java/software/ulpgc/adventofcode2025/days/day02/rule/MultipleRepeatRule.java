package software.ulpgc.adventofcode2025.days.day02.rule;

/**
 * Validation rule that checks if an ID consists of a pattern repeated a prime number of times.
 *
 * This rule validates IDs where a base pattern is repeated exactly p times,
 * where p is a prime number that divides the total length evenly. For example,
 * "123123123" is valid (pattern "123" repeated 3 times).
 */
public class MultipleRepeatRule implements IdRule {

    /**
     * Array of prime numbers used to check for valid repetition counts.
     * Covers primes up to 97, allowing validation of IDs with various lengths.
     */
    private static final int[] PRIMES = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
            53, 59, 61, 67, 71, 73, 79, 83, 89, 97
    };

    /**
     * Evaluates whether an ID consists of a pattern repeated a prime number of times.
     *
     * The method checks if the ID's length is divisible by any prime number,
     * and if so, verifies that the ID consists of a base pattern repeated that
     * many times.
     *
     * For example:
     * - "123123" → valid (pattern "123" repeated 2 times, where 2 is prime)
     * - "123123123" → valid (pattern "123" repeated 3 times, where 3 is prime)
     * - "1212" → valid (pattern "12" repeated 2 times)
     * - "123456" → invalid (no repeating pattern)
     *
     * @param id The ID to evaluate
     * @return The ID value if it has a prime-repetition pattern, 0 otherwise
     */
    @Override
    public long evaluate(long id){
        String idStr = String.valueOf(id);
        int length = idStr.length();
        for (int p : PRIMES){
            if(length % p == 0){
                if(isRepeated(idStr, p)) return id;
            }
        }
        return 0;
    }

    /**
     * Checks if a string consists of a base pattern repeated exactly p times.
     *
     * Divides the string into p equal parts and verifies that each part matches
     * the first part. The length of each part is calculated as the total length
     * divided by p.
     *
     * @param idStr The ID string to check
     * @param p The number of repetitions to verify
     * @return true if the string consists of a pattern repeated p times, false otherwise
     */
    private boolean isRepeated(String idStr, int p) {
        int partLen = idStr.length() / p;
        String firstPart = idStr.substring(0, partLen);

        for(int i = 0; i < p; i++){
            int start = i * partLen;
            if(!idStr.startsWith(firstPart, i*partLen)) return false;
        }
        return true;
    }
}