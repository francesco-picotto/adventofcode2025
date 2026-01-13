package software.ulpgc.adventofcode2025.days.day03;

public class AdvancedBankRule implements BankRule {
    private static final int J_LENGTH = 12;


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
