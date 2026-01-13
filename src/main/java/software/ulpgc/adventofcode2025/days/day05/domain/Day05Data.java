package software.ulpgc.adventofcode2025.days.day05.domain;

import java.util.List;

/**
 * Data transfer object containing the input data for Day 05 inventory analysis.
 *
 * This record encapsulates the two pieces of input data needed for the analysis:
 * fresh ingredient ranges and available ingredient IDs. Being a record provides
 * immutability and automatic generation of constructors, getters, equals, hashCode,
 * and toString methods.
 *
 * @param ranges List of string representations of fresh ingredient ranges (e.g., "100-200")
 * @param ids List of string representations of available ingredient IDs (e.g., "150")
 */
public record Day05Data(List<String> ranges, List<String> ids) {}