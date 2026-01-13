package software.ulpgc.adventofcode2025.days.day03.service;

import software.ulpgc.adventofcode2025.days.day03.rule.BankRule;

import java.util.List;

public class BankProcessor {
    private final BankRule rule;

    /**
     * Constructs a BankProcessor with the specified extraction rule.
     *
     * Uses dependency injection to receive the rule that will be applied
     * to each bank code string for extracting numerical values.
     *
     * @param rule The rule to use for extracting values from bank codes
     */
    public BankProcessor(BankRule rule) {
        this.rule = rule;
    }

    /**
     * Processes a list of bank code strings and calculates the sum of all extracted values.
     *
     * Filters out empty strings, applies the configured rule to each valid bank code
     * to extract a numerical value, and sums all extracted values to produce the
     * final result.
     *
     * @param banks A list of bank code strings to process
     * @return The sum of all values extracted from the bank codes
     */
    public long solve(List<String> banks) {
        return banks.stream()
                .filter(bank -> !bank.isEmpty())
                .mapToLong(rule::evaluate)
                .sum();
    }
}