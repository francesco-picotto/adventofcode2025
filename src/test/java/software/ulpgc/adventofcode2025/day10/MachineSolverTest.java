package software.ulpgc.adventofcode2025.day10;

import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day10.JoltageSolver;
import software.ulpgc.adventofcode2025.days.day10.LightConfigurationSolver;
import software.ulpgc.adventofcode2025.days.day10.Machine;
import software.ulpgc.adventofcode2025.days.day10.MachineSolver;

import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for LightConfigurationSolver and JoltageSolver.
 */
public class MachineSolverTest {

    @Test
    public void testLightConfigurationSolver() {
        // Setup: Define a target state of [true, true] (##)
        // Button 1 toggles light 0, Button 2 toggles light 1
        List<Boolean> target = List.of(true, true);
        List<Set<Integer>> buttons = List.of(Set.of(0), Set.of(1));

        // Target Joltage is irrelevant for this solver
        Machine machine = new Machine(target, buttons, List.of());

        MachineSolver solver = new LightConfigurationSolver();
        long result = solver.solve(machine);

        // Expecting 2 presses (one for each button to reach the state)
        assertEquals(2, result, "LightConfigurationSolver should return 2 presses");
    }

    @Test
    public void testJoltageSolver() {
        // Setup: Target Joltage {2, 2}. Buttons subtract 1 from specific indices.
        // Button 0 affects index 0, Button 1 affects index 1.
        List<Integer> targetJoltage = List.of(2, 2);
        List<Set<Integer>> buttons = List.of(Set.of(0), Set.of(1));

        // Target lights are irrelevant for this solver
        Machine machine = new Machine(List.of(), buttons, targetJoltage);

        MachineSolver solver = new JoltageSolver();
        long result = solver.solve(machine);

        // Logic: 2 can be reached by (1+1) * 2 or similar recursive logic
        // In the simplest case where buttons match target, it should be consistent.
        assertEquals(4, result, "JoltageSolver should return the correct minimum presses");
    }

    @Test
    public void testComplexJoltageScenario() {
        // Setup: A single button affecting multiple lights
        List<Integer> targetJoltage = List.of(3, 3);
        List<Set<Integer>> buttons = List.of(Set.of(0, 1)); // One button affects both

        Machine machine = new Machine(List.of(), buttons, targetJoltage);
        MachineSolver solver = new JoltageSolver();
        long result = solver.solve(machine);

        // To reach 3 with a "double and add" logic:
        // Step 1: 0 -> (press button) -> 1
        // Step 2: 1 -> (double) -> 2
        // Step 3: 2 -> (press button) -> 3
        // Result: 1 (step 1) + 1 (step 3) = 2 total button presses?
        // The solver logic: result = current + 2 * subResult.
        // result = 1 + 2 * (compute(halve(3-1))) = 1 + 2 * (compute(1)) = 1 + 2 * (1) = 3.
        assertEquals(3, result, "Complex Joltage scenario failed");
    }
}