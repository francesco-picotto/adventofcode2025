package software.ulpgc.adventofcode2025.days.day08.analyzer;
import software.ulpgc.adventofcode2025.days.day08.domain.Connection;
import software.ulpgc.adventofcode2025.days.day08.domain.JunctionBox;
import software.ulpgc.adventofcode2025.days.day08.domain.UnionFind;

import java.util.*;

/**
 * Basic analyzer that forms circuit components using the first N connections.
 *
 * This analyzer uses a limited number of connections (typically the shortest ones)
 * to form components, then calculates the product of the three largest component
 * sizes. This represents a partial circuit formation where not all connections
 * are made, resulting in multiple separate components.
 *
 * The approach is similar to Kruskal's MST but stops after a fixed number of
 * connections rather than when a spanning tree is formed.
 */
public class BasicCircuitAnalyzer implements CircuitAnalyzer {
    /**
     * The maximum number of connections to process when forming components.
     */
    private static final int CONNECTION_LIMIT = 1000;

    /**
     * Analyzes the circuit by forming components with limited connections.
     *
     * Algorithm:
     * 1. Creates a Union-Find structure for all junction boxes
     * 2. Processes only the first 1000 connections (typically shortest by distance)
     * 3. Forms components by uniting boxes connected by these connections
     * 4. Identifies all distinct components
     * 5. Finds the three largest components by size
     * 6. Returns the product of these three sizes
     *
     * This approach answers questions like "What is the structure when only
     * the shortest connections are available?" or "How fragmented is the circuit
     * before all connections are made?"
     *
     * Example:
     * - 10 boxes, first 1000 connections form 3 components
     * - Component sizes: [5, 3, 2]
     * - Result: 5 × 3 × 2 = 30
     *
     * @param boxes List of junction boxes in the circuit
     * @param allConnections List of all possible connections, pre-sorted by distance
     * @return The product of the three largest component sizes
     */
    @Override
    public long analyze(List<JunctionBox> boxes, List<Connection> allConnections) {

        UnionFind uf = new UnionFind(boxes.size());

        // Process only the first CONNECTION_LIMIT connections
        allConnections.stream()
                .limit(CONNECTION_LIMIT)
                .forEach(c -> uf.union(c.a().id(), c.b().id()));

        // Find all distinct component roots, get their sizes, sort descending,
        // take top 3, and multiply them together
        return boxes.stream()
                .map(box -> uf.find(box.id()))  // Get root of each box's component
                .distinct()                      // Keep only unique components
                .map(root -> uf.getSize(root))  // Get size of each component
                .sorted(Comparator.reverseOrder()) // Sort by size descending
                .limit(3)                        // Take the three largest
                .mapToLong(Integer::longValue)
                .reduce(1, (a, b) -> a*b);      // Multiply them together

    }
}