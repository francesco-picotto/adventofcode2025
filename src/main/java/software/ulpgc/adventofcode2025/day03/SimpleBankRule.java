package software.ulpgc.adventofcode2025.day03;

public class SimpleBankRule implements BankRule {
    private  int lastIndex = 0;

    @Override
    public long evaluate (String bank) {
        int len = bank.length();
        char dec = maxSearch(bank, 0, len-2);
        char unit = maxSearch(bank, lastIndex + 1, len - 1);

        return Long.parseLong("" + dec + unit);
    }

    private char maxSearch(String s, int start, int end) {
        char max = '0';
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) > max) {
                max = s.charAt(i);
                lastIndex = i;
            }
        }
        return max;
    }
}
