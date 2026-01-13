package software.ulpgc.adventofcode2025.core;

import java.util.List;

/**
 * Strategy interface for mapping raw input lines into domain-specific objects.
 *
 * This interface defines a contract for transforming text-based input data (typically
 * read from files) into strongly-typed domain objects. Each day's puzzle can provide
 * its own implementation to parse input in the format specific to that problem.
 *
 * This follows the Strategy pattern, allowing the InputProvider to work with any
 * type of input transformation without knowing the specific parsing logic.
 *
 * @param <T> the type of domain object to produce from the input lines
 */
public interface InputMapper<T> {

    /**
     * Maps a list of input lines into a domain-specific object.
     *
     * Implementations should parse the provided lines according to the specific
     * format expected for the problem at hand, handling any necessary validation,
     * transformation, or error checking.
     *
     * Example implementations might:
     * - Parse CSV data into a list of objects
     * - Convert grid representations into 2D arrays
     * - Extract structured data from formatted text
     * - Build complex domain objects from multi-line specifications
     *
     * @param lines the raw input lines read from a file
     * @return a domain object of type T constructed from the input lines
     * @throws RuntimeException if the input cannot be parsed or is invalid
     */
    T map(List<String> lines);
}