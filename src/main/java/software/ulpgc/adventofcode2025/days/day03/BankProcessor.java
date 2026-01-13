package software.ulpgc.adventofcode2025.days.day03;

import java.util.List;

public class BankProcessor {
    private final BankRule rule;

    // Dependency Injection tramite costruttore
    public BankProcessor(BankRule rule) {
        this.rule = rule;
    }

    public long solve(List<String> banks) {
        return banks.stream()
                .filter(bank -> !bank.isEmpty())
                .mapToLong(rule::evaluate)
                .sum();
    }
}