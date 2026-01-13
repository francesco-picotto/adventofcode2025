package software.ulpgc.adventofcode2025.days.day03;

public class SimpleBankRule implements BankRule {

    @Override
    public long evaluate (String bank) {
        int len = bank.length();
        int firstIdx = BankRule.findMaxIndex(bank, 0, len-2);
        int secondIdx = BankRule.findMaxIndex(bank, firstIdx + 1, len - 1);

        return Long.parseLong("" + bank.charAt(firstIdx) + bank.charAt(secondIdx));
    }
}
