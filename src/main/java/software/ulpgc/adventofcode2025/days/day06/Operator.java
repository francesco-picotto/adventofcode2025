package software.ulpgc.adventofcode2025.days.day06;

import java.util.List;
import java.util.function.BinaryOperator;

public enum Operator {
    ADD('+', (a, b) -> a + b, 0L),
    MULTIPLY('*', (a, b) -> a * b, 1L);

    private final char symbol;
    private final BinaryOperator<Long> operation;
    private final long identity;

    Operator(char symbol, BinaryOperator<Long> operation, long identity) {
        this.symbol = symbol;
        this.operation = operation;
        this.identity = identity;
    }

    public long apply(List<Long> numbers) {
        return numbers.stream().reduce(identity, operation);
    }

    public static Operator fromChar(char c) {
        for (Operator op : values()) {
            if (op.symbol == c) return op;
        }
        throw new IllegalArgumentException("Operatore non supportato: " + c);
    }
}