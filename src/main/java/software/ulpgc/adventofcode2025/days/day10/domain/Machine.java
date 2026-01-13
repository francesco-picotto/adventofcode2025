package software.ulpgc.adventofcode2025.days.day10.domain;

import java.util.List;
import java.util.Set;

/**
 * Represents a machine puzzle configuration with lights, buttons, and joltage targets.
 * This immutable record encapsulates the state and constraints of a machine that needs
 * to be solved by manipulating buttons to reach target configurations.
 *
 * @param targetLights the target configuration of lights (true = on, false = off)
 * @param buttons a list of button configurations, where each button affects specific
 *                light/joltage indices (represented as a set of integers)
 * @param targetJoltage the target joltage values that need to be achieved
 */
public record Machine(List<Boolean> targetLights, List<Set<Integer>> buttons, List<Integer> targetJoltage) {
}