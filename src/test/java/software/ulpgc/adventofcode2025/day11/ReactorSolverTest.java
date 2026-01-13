package software.ulpgc.adventofcode2025.day11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day11.solver.MandatoryNodeSolver;
import software.ulpgc.adventofcode2025.days.day11.domain.ReactorMap;
import software.ulpgc.adventofcode2025.days.day11.solver.ReactorSolver;
import software.ulpgc.adventofcode2025.days.day11.solver.SimplePathSolver;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactorSolverTest {

    private ReactorMap exampleMap;

    @BeforeEach
    void setUp() {
        // Defining a simple directed graph for testing
        // you -> A -> out (1 path)
        // you -> B -> out (1 path)
        // svr -> fft -> dac -> out (1 path for mandatory chain)
        Map<String, Set<String>> connections = new HashMap<>();
        connections.put("you", Set.of("A", "B"));
        connections.put("A", Set.of("out"));
        connections.put("B", Set.of("out"));

        // Paths for MandatoryNodeSolver test
        connections.put("svr", Set.of("fft", "dac"));
        connections.put("fft", Set.of("dac"));
        connections.put("dac", Set.of("fft", "out"));

        exampleMap = new ReactorMap(connections);
    }

    @Test
    void testSimplePathSolver() {
        // SimplePathSolver counts paths from "you" to "out"
        // Expected paths: you->A->out and you->B->out (Total: 2)
        ReactorSolver solver = new SimplePathSolver();
        long result = solver.solve(exampleMap);

        assertEquals(2L, result, "SimplePathSolver should find exactly 2 paths from 'you' to 'out'");
    }

    @Test
    void testMandatoryNodeSolver() {
        // MandatoryNodeSolver calculates the max chain between:
        // Path A: svr -> fft -> dac -> out
        // Path B: svr -> dac -> fft -> out

        // From our setup:
        // svr -> fft (1), fft -> dac (1), dac -> out (1) => Product = 1
        // svr -> dac (1), dac -> fft (1), fft -> out (0) => Product = 0
        // Max(1, 0) = 1

        ReactorSolver solver = new MandatoryNodeSolver();
        long result = solver.solve(exampleMap);

        assertEquals(1L, result, "MandatoryNodeSolver should return the maximum valid chain product");
    }
}