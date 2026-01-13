package software.ulpgc.adventofcode2025.days.day05;

import software.ulpgc.adventofcode2025.core.InputMapper;
import software.ulpgc.adventofcode2025.days.day05.domain.Day05Data;

import java.util.Arrays;
import java.util.List;

/**
 * Input mapper for Day 5 that parses input with two distinct sections separated by blank lines.
 * This mapper splits the input into two parts, typically representing different types of data
 * or different phases of a problem (e.g., rules and data, constraints and values, etc.).
 */
public class Day05Mapper implements InputMapper<Day05Data> {

    /**
     * Maps input lines into a Day05Data object containing two separate sections.
     *
     * The input format expects two sections separated by one or more blank lines.
     * Each section is split into individual lines and stored separately in the
     * resulting Day05Data object.
     *
     * Processing steps:
     * 1. Joins all lines into a single string with newlines
     * 2. Splits on blank line patterns (one or more newlines with optional whitespace)
     * 3. Creates a Day05Data object with both sections as separate lists
     *
     * Example input:
     * Section 1:
     * rule1
     * rule2
     *
     * Section 2:
     * data1
     * data2
     *
     * Result: Day05Data with two lists, one for each section
     *
     * @param lines the raw input lines from the file
     * @return a Day05Data object containing both sections as separate lists of strings
     */
    @Override
    public Day05Data map(List<String> lines) {
        // Join all lines into a single string to facilitate section splitting
        String content = String.join("\n", lines);

        // Split on blank lines (one or more newlines with optional whitespace between)
        String[] sections = content.split("\\n\\s*\\n");

        // Create Day05Data with both sections split into individual lines
        return new Day05Data(
                Arrays.asList(sections[0].split("\\R")),  // First section
                Arrays.asList(sections[1].split("\\R"))   // Second section
        );
    }
}