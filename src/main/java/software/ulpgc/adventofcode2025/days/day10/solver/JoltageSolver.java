package software.ulpgc.adventofcode2025.days.day10.solver;

import software.ulpgc.adventofcode2025.days.day10.domain.Machine;

import java.util.*;

/**
 * Solver implementation that finds the minimum number of button presses to achieve
 * target joltage values using dynamic programming with memoization.
 *
 * This solver uses a recursive approach where button presses decrease joltage values,
 * and when all values are even and non-negative, they can be halved. The algorithm
 * explores all possible button press combinations and division steps to find the
 * minimum total presses required.
 */
public class JoltageSolver implements MachineSolver {
    private static final long INF = Long.MAX_VALUE;
    private final Map<List<Integer>, Long> memo = new HashMap<>();

    /**
     * Solves a machine's joltage puzzle to find the minimum button presses required.
     * Clears the memoization cache before solving to ensure a fresh computation.
     *
     * @param machine the machine configuration containing target joltage and button effects
     * @return the minimum number of button presses needed to reach all joltage targets at zero,
     *         or Long.MAX_VALUE if the target is unreachable
     */
    @Override
    public long solve(Machine machine) {
        memo.clear();
        return compute(new ArrayList<>(machine.targetJoltage()), machine.buttons());
    }

    /**
     * Recursively computes the minimum button presses needed to reduce all target
     * joltage values to zero using dynamic programming with memoization.
     *
     * The algorithm explores all possible combinations of button presses that lead
     * to states where all values can be halved, then recursively solves the halved state.
     *
     * Base cases:
     * - All targets are zero: 0 presses needed (success)
     * - Any target is negative: unreachable (invalid state)
     * - Previously computed state: return memoized result
     *
     * @param target the current joltage values to reduce
     * @param buttons the list of button effects (each button decreases specific indices by 1)
     * @return the minimum number of presses needed, or INF if impossible
     */
    private long compute(List<Integer> target, List<Set<Integer>> buttons) {
        // Base case: all targets reached
        if (isTargetReached(target)) return 0;

        // Invalid state: negative values mean we overshot
        if (isInvalid(target)) return INF;

        // Return cached result if already computed
        if (memo.containsKey(target)) return memo.get(target);

        Long minPresses = INF;
        int numButtons = buttons.size();

        // Try all possible combinations of button presses (2^numButtons possibilities)
        for (int i = 0; i < (1 << numButtons); i++) {
            // Apply the selected buttons to get the next state
            List<Integer> nextState = applyButtons(target, buttons, i);

            // Check if we can perform a division step (all values must be even and non-negative)
            if (canDivideByTwo(nextState)) {
                // Recursively solve the halved state
                long subResult = compute(halveState(nextState), buttons);

                if (subResult != INF) {
                    // Current step cost: button presses now + 2 * (cost of halved subproblem)
                    // The factor of 2 accounts for the cost propagation after halving
                    int currentStepPresses = Integer.bitCount(i);
                    minPresses = Math.min(minPresses, currentStepPresses + 2 * subResult);
                }
            }
        }

        // Cache and return the result
        memo.put(target, minPresses);
        return minPresses;
    }

    /**
     * Checks if all target joltage values have been reduced to zero.
     *
     * @param target the current joltage values
     * @return true if all values are zero, false otherwise
     */
    private boolean isTargetReached(List<Integer> target) {
        return target.stream().allMatch(v -> v == 0);
    }

    /**
     * Checks if the current state is invalid (contains negative values).
     * Negative values indicate that we've applied too many decrements and
     * cannot reach the target from this state.
     *
     * @param target the current joltage values
     * @return true if any value is negative, false otherwise
     */
    private boolean isInvalid(List<Integer> target) {
        return target.stream().anyMatch(v -> v < 0);
    }

    /**
     * Checks if all values in the state can be divided by two.
     * A state can be halved only if all values are non-negative and even.
     *
     * @param state the current joltage values
     * @return true if all values are non-negative and even, false otherwise
     */
    private boolean canDivideByTwo(List<Integer> state) {
        return state.stream().allMatch(v -> v >= 0 && v % 2 == 0);
    }

    /**
     * Halves all values in the state by dividing each by 2.
     * This represents a "division step" in the algorithm after reaching
     * a state where all values are even.
     *
     * @param state the current joltage values (must all be even)
     * @return a new list with all values halved
     */
    private List<Integer> halveState(List<Integer> state) {
        return state.stream().map(v -> v / 2).toList();
    }

    /**
     * Applies a combination of button presses to the current state.
     * Each button decreases the joltage values at specific indices by 1.
     * The bitmask determines which buttons are pressed in this step.
     *
     * @param current the current joltage values
     * @param buttons the list of button effects (sets of indices each button affects)
     * @param bitmask a bitmask where bit b indicates whether button b is pressed
     * @return a new state with the button effects applied
     */
    private List<Integer> applyButtons(List<Integer> current, List<Set<Integer>> buttons, int bitmask) {
        List<Integer> next = new ArrayList<>(current);

        // Check each button position in the bitmask
        for (int b = 0; b < buttons.size(); b++) {
            // If this button is pressed (bit is set)
            if (((bitmask >> b) & 1) == 1) {
                // Decrease all joltage values affected by this button
                for (int idx : buttons.get(b)) {
                    if (idx < next.size()) {
                        next.set(idx, next.get(idx) - 1);
                    }
                }
            }
        }
        return next;
    }
}