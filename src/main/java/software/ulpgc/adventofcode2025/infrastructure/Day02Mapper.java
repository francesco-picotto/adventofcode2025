package software.ulpgc.adventofcode2025.infrastructure;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day02.IdRange;

import java.util.Arrays;
import java.util.List;

public class Day02Mapper implements InputMapper<List<String>> {
    @Override
    public List<String> map(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> line.split(",")) // Trasforma ogni riga in un array ["1102", "2949"]
                .flatMap(Arrays::stream)      // "Schiaccia" gli array in un unico flusso di stringhe
                .toList();
    }
}