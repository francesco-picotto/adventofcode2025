package software.ulpgc.adventofcode2025.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Utility class for reading and processing input files into domain objects.
 *
 * This class provides a centralized mechanism for loading puzzle input data from files
 * and transforming it into domain-specific objects using pluggable mapping strategies.
 * It handles file I/O operations and delegates the parsing logic to InputMapper implementations.
 *
 * The InputProvider follows the Strategy pattern, separating file reading concerns from
 * parsing logic, making it easy to work with different input formats across various puzzles.
 */
public class InputProvider {
    private final String basePath;

    /**
     * Constructs an InputProvider with a base directory path for input files.
     *
     * All subsequent file reads will be relative to this base path, allowing for
     * consistent input file organization across different puzzle solutions.
     *
     * @param basePath the base directory path where input files are located
     *                 (e.g., "src/main/resources/inputs")
     */
    public InputProvider(String basePath) {
        this.basePath = basePath;
    }

    /**
     * Reads an input file and transforms its contents into a domain object using the provided mapper.
     *
     * This method performs the following operations:
     * 1. Reads all lines from the specified file (located relative to basePath)
     * 2. Delegates the transformation of lines to the provided InputMapper
     * 3. Returns the resulting domain object
     *
     * The generic type parameter allows this method to work with any domain type,
     * providing type safety while maintaining flexibility across different puzzle inputs.
     *
     * Example usage:
     * <pre>
     * InputProvider provider = new InputProvider("src/main/resources/inputs");
     * List&lt;Integer&gt; numbers = provider.provide("day1.txt", new NumberListMapper());
     * Grid grid = provider.provide("day2.txt", new GridMapper());
     * </pre>
     *
     * @param <T> the type of domain object to produce
     * @param fileName the name of the input file (relative to basePath)
     * @param mapper the InputMapper implementation that knows how to parse this file's format
     * @return the domain object of type T created by the mapper from the file contents
     * @throws RuntimeException if the file cannot be read (wraps IOException with context)
     */
    public <T> T provide(String fileName, InputMapper<T> mapper) {
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Path.of(basePath, fileName));

            // Delegate parsing to the mapper
            return mapper.map(lines);
        } catch (IOException e) {
            // Wrap IOException with more context and convert to unchecked exception
            throw new RuntimeException("Impossible to read the file: " + fileName, e);
        }
    }
}