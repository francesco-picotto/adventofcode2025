package software.ulpgc.adventofcode2025.days.day08.analyzer;

import software.ulpgc.adventofcode2025.days.day08.domain.Connection;
import software.ulpgc.adventofcode2025.days.day08.domain.JunctionBox;

import java.util.List;

/**
 * Strategy interface for analyzing circuit connectivity patterns.
 *
 * Implementations of this interface define different approaches to analyzing
 * how junction boxes are connected in a circuit. Different analyzers may focus
 * on different aspects such as component size distribution or finding critical
 * connections that unify the entire circuit.
 */
public interface CircuitAnalyzer {
    /**
     * Analyzes circuit connectivity and returns a numerical result.
     *
     * Processes the junction boxes and their potential connections to compute
     * a metric based on the analyzer's specific strategy. The connections are
     * typically pre-sorted by distance to enable algorithms like Kruskal's MST.
     *
     * @param boxes List of junction boxes in the circuit
     * @param allConnections List of possible connections between boxes, typically sorted by distance
     * @return The analysis result (e.g., product of component sizes or connection metric)
     */
    long analyze(List<JunctionBox> boxes, List<Connection> allConnections);
}