package software.ulpgc.adventofcode2025.days.day10.solver;

import software.ulpgc.adventofcode2025.days.day10.domain.Machine;
import java.util.*;

/**
 * Solver implementation that finds the minimum number of button presses to achieve
 * a target light configuration using breadth-first search (BFS).
 *
 * This solver treats the problem as a state-space search where each button press
 * toggles specific lights. It explores all possible button press sequences to find
 * the shortest path to the target configuration.
 */
public class LightConfigurationSolver implements MachineSolver {

    /**
     * Solves a machine's light configuration puzzle using BFS to find the minimum
     * number of button presses required to reach the target light state.
     *
     * The algorithm starts with all lights off and explores all possible button
     * press sequences until the target configuration is reached.
     *
     * @param machine the machine configuration containing target lights and button effects
     * @return the minimum number of button presses needed to reach the target configuration,
     *         or 0 if the target cannot be reached
     */
    @Override
    public long solve(Machine machine) {
        /**
         * Internal record representing a state in the search space.
         *
         * @param lights the current configuration of lights (true = on, false = off)
         * @param presses the number of button presses taken to reach this state
         */
        record State(List<Boolean> lights, int presses) {}

        // Track visited states to avoid revisiting the same configuration
        Set<List<Boolean>> visited = new HashSet<>();
        Queue<State> queue = new ArrayDeque<>();

        // Initialize with all lights off (starting state)
        List<Boolean> initial = Collections.nCopies(machine.targetLights().size(), false);
        queue.add(new State(initial, 0));
        visited.add(initial);

        // Perform BFS to find the shortest path to the target
        while (!queue.isEmpty()) {
            State current = queue.poll();

            // Check if we've reached the target configuration
            if (current.lights.equals(machine.targetLights())) {
                return current.presses;
            }

            // Try pressing each button and explore resulting states
            for (Set<Integer> button : machine.buttons()) {
                List<Boolean> nextState = toggle(current.lights, button);

                // Only explore new states we haven't visited before
                if (visited.add(nextState)) {
                    queue.add(new State(nextState, current.presses + 1));
                }
            }
        }

        // Target configuration is unreachable
        return 0;
    }

    /**
     * Toggles the lights affected by a button press.
     * Creates a new light configuration by flipping the state of all lights
     * whose indices are specified in the button configuration.
     *
     * @param current the current light configuration
     * @param buttonIndices the set of light indices that this button affects
     * @return a new light configuration with the specified lights toggled
     */
    private List<Boolean> toggle(List<Boolean> current, Set<Integer> buttonIndices) {
        List<Boolean> next = new ArrayList<>(current);
        for (int idx : buttonIndices) {
            if (idx < next.size()) {
                next.set(idx, !next.get(idx));
            }
        }
        return next;
    }
}