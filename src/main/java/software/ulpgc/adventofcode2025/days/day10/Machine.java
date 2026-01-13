package software.ulpgc.adventofcode2025.days.day10;

import java.util.List;
import java.util.Set;

public record Machine (List<Boolean> targetLights, List<Set<Integer>> buttons, List<Integer> targetJoltage) {}
