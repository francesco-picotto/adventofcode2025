package software.ulpgc.adventofcode2025.days.day08.domain;

import java.util.List;

/**
 * Data transfer object containing the input data for Day 08 circuit analysis.
 *
 * This record encapsulates the two pieces of input data needed for circuit analysis:
 * junction boxes positioned in 3D space and possible connections between them.
 * Being a record provides immutability and automatic generation of constructors,
 * getters, equals, hashCode, and toString methods.
 *
 * @param boxes List of junction boxes with their IDs and 3D positions
 * @param connections List of possible connections between boxes, typically sorted by distance
 */
public record Day08Data(List<JunctionBox> boxes, List<Connection> connections) {}