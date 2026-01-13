package software.ulpgc.adventofcode2025.days.day06.domain;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Enumeration of mathematical operators supported in worksheet problems.
 *
 * This enum encapsulates the behavior of different mathematical operations,
 * storing their symbol representation, the operation itself, and the identity
 * value needed for reduction. Each operator can apply its operation to a list
 * of numbers using the reduce pattern with the appropriate identity value.
 */
public enum Operator {
    /**
     * Addition operator with symbol '+' and identity value 0.
     * Reduces a list of numbers by summing them: a + b + c + ...
     */
    ADD('+', (a, b) -> a + b, 0L),

    /**
     * Multiplication operator with symbol '*' and identity value 1.
     * Reduces a list of numbers by multiplying them: a × b × c × ...
     */
    MULTIPLY('*', (a, b) -> a * b, 1L);

    private final char symbol;
    private final BinaryOperator<Long> operation;
    private final long identity;

    /**
     * Constructs an Operator with its symbol, operation, and identity value.
     *
     * @param symbol The character representing this operator (e.g., '+', '*')
     * @param operation The binary operation to apply between two numbers
     * @param identity The identity value for the operation (0 for addition, 1 for multiplication)
     */
    Operator(char symbol, BinaryOperator<Long> operation, long identity) {
        this.symbol = symbol;
        this.operation = operation;
        this.identity = identity;
    }

    /**
     * Applies this operator to a list of numbers using reduction.
     *
     * Reduces the list of numbers by repeatedly applying the binary operation,
     * starting with the identity value. For example:
     * - ADD.apply([5, 3, 2]) → 0 + 5 + 3 + 2 = 10
     * - MULTIPLY.apply([5, 3, 2]) → 1 × 5 × 3 × 2 = 30
     *
     * The identity value ensures correct behavior with empty lists or single elements:
     * - ADD.apply([]) → 0
     * - MULTIPLY.apply([5]) → 5
     *
     * @param numbers The list of numbers to reduce
     * @return The result of applying the operation to all numbers
     */
    public long apply(List<Long> numbers) {
        return numbers.stream().reduce(identity, operation);
    }

    /**
     * Finds the Operator enum value corresponding to a character symbol.
     *
     * Searches through all operator values to find the one with the matching
     * symbol character. This is used when parsing operator symbols from the
     * worksheet representation.
     *
     * @param c The character symbol to look up (e.g., '+', '*')
     * @return The Operator enum value with the matching symbol
     * @throws IllegalArgumentException if no operator matches the given character
     */
    public static Operator fromChar(char c) {
        for (Operator op : values()) {
            if (op.symbol == c) return op;
        }
        throw new IllegalArgumentException("Operatore non supportato: " + c);
    }
}