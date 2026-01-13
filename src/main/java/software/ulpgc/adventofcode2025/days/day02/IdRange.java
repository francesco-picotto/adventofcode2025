package software.ulpgc.adventofcode2025.days.day02;

import java.util.stream.LongStream;

public record IdRange(long start, long end) {

    // Metodo factory per gestire il parsing della stringa "100-200"
    public static IdRange parse(String rangeStr) {
        String[] parts = rangeStr.split("-");
        return new IdRange(
                Long.parseLong(parts[0].trim()),
                Long.parseLong(parts[1].trim())
        );
    }

    // Spostiamo qui la logica di calcolo specifica per il range
    public long sumValidIds(IdRule rule) {
        return LongStream.rangeClosed(start, end)
                .map(rule::evaluate)
                .sum();
    }
}
