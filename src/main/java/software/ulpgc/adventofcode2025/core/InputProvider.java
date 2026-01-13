package software.ulpgc.adventofcode2025.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputProvider {
    private final String basePath;

    public InputProvider(String basePath) {
        this.basePath = basePath;
    }

    public <T> T provide(String fileName, InputMapper<T> mapper) {
        try {
            List<String> lines = Files.readAllLines(Path.of(basePath, fileName));
            return mapper.map(lines);
        } catch (IOException e) {
            throw new RuntimeException("Impossible to read the file: " + fileName, e);
        }
    }
}