package software.ulpgc.adventofcode2025.days.day03;

public interface BankRule {
    long evaluate (String bank);

    //Utility method that is reusable
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
