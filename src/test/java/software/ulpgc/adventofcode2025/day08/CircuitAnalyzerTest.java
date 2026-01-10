package software.ulpgc.adventofcode2025.day08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CircuitAnalyzerTest {
    private List<JunctionBox> boxes;
    private List<Connection> connections;

    @BeforeEach
    void setUp() {
        // Initialize a small set of boxes to create a predictable environment
        boxes = new ArrayList<>();
        boxes.add(new JunctionBox(0, new Point3D(0, 0, 0)));
        boxes.add(new JunctionBox(1, new Point3D(1, 0, 0))); // Close to 0
        boxes.add(new JunctionBox(2, new Point3D(10, 10, 10)));
        boxes.add(new JunctionBox(3, new Point3D(11, 10, 10))); // Close to 2

        // Generate all possible connections between boxes
        connections = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                JunctionBox a = boxes.get(i);
                JunctionBox b = boxes.get(j);
                connections.add(new Connection(a, b, a.distanceTo(b)));
            }
        }

        // Analyzers expect connections to be sorted by distance (Ascending)
        Collections.sort(connections);
    }

    @Test
    void testBasicCircuitAnalyzer() {
        CircuitAnalyzer analyzer = new BasicCircuitAnalyzer();

        // BasicCircuitAnalyzer groups boxes into components using the first 1000 connections
        // Then it multiplies the sizes of the 3 largest components
        long result = analyzer.analyze(boxes, connections);

        // In our setup, all 4 boxes will likely merge into one component because 1000 >> 4.
        // The analyzer calculates size of largest components.
        assertTrue(result > 0, "Result should be a positive product of component sizes");
    }

    @Test
    void testAdvanceCircuitAnalyzer() {
        CircuitAnalyzer analyzer = new AdvanceCircuitAnalyzer();

        // AdvanceCircuitAnalyzer returns (a.x * b.x) of the connection that
        // finally merges all boxes into a single component
        long result = analyzer.analyze(boxes, connections);

        // In our setup:
        // 0-1 connects (dist 1)
        // 2-3 connects (dist 1)
        // The connection that merges these two islands will trigger the return
        assertNotEquals(0, result, "The analyzer should return the product of x-coordinates upon total connection");
    }
}