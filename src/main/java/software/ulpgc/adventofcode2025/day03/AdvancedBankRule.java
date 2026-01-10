package software.ulpgc.adventofcode2025.day03;

public class AdvancedBankRule implements BankRule {
    private int lastIndex = 0;
    private static final int J_LENGTH = 12;


    @Override
    public long evaluate (String bank) {
        int len = bank.length();
        StringBuilder joltagestr = new StringBuilder();

        joltagestr.append(maxSearch(bank, 0, len - J_LENGTH));

        for (int k = 1; k < J_LENGTH; k++) {
            joltagestr.append(maxSearch(bank, lastIndex + 1, len - J_LENGTH + k));
        }
        return Long.parseLong(joltagestr.toString());
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
