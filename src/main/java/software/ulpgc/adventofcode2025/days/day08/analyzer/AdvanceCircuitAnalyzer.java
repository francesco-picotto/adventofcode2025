package software.ulpgc.adventofcode2025.days.day08.analyzer;
import software.ulpgc.adventofcode2025.days.day08.domain.Connection;
import software.ulpgc.adventofcode2025.days.day08.domain.JunctionBox;
import software.ulpgc.adventofcode2025.days.day08.domain.UnionFind;

import java.util.*;

/**
 * Advanced analyzer that finds the critical connection that unifies the entire circuit.
 *
 * This analyzer progressively adds connections in order (typically by distance)
 * until all junction boxes form a single connected component. It identifies the
 * specific connection that completes the circuit and returns a metric based on
 * that connection's endpoints.
 *
 * This is similar to finding the last edge added in Kruskal's MST algorithm,
 * which represents the critical link that brings the entire graph together.
 */
public class AdvanceCircuitAnalyzer implements CircuitAnalyzer {
    /**
     * Analyzes the circuit to find the connection that unifies all components.
     *
     * Algorithm:
     * 1. Creates a Union-Find structure for all junction boxes
     * 2. Processes connections in order (pre-sorted by distance)
     * 3. For each connection, checks if it would unite two separate components
     * 4. When a connection is added, checks if all boxes are now in one component
     * 5. Returns a metric based on the x-coordinates of the unifying connection's endpoints
     *
     * The key insight is that we're looking for the moment when the component
     * count drops from 2 to 1, indicating full connectivity.
     *
     * Example:
     * - Start with 10 components (10 separate boxes)
     * - Add connections, reducing components: 10 → 9 → 8 → ... → 2
     * - Add final connection between boxes A and B, reducing 2 → 1
     * - Result: A.x × B.x (product of x-coordinates)
     *
     * The x-coordinate product serves as an identifier for the critical connection
     * that completed the circuit.
     *
     * @param boxes List of junction boxes in the circuit
     * @param allConnections List of all possible connections, pre-sorted by distance
     * @return The product of the x-coordinates of the boxes connected by the unifying connection,
     *         or 0 if the circuit cannot be fully connected
     */
    @Override
    public long analyze(List<JunctionBox> boxes, List<Connection> allConnections) {
        UnionFind uf = new UnionFind(boxes.size());

        for(Connection c : allConnections){
            int rootA = uf.find(c.a().id());
            int rootB = uf.find(c.b().id());

            // Only process if this connection would unite two different components
            if(rootA != rootB){
                uf.union(rootA, rootB);

                // Check if this connection unified the entire circuit
                if(uf.getComponentsCount() == 1){
                    // Return the product of x-coordinates as the result
                    return (long) c.a().position().x() * (long) c.b().position().x();
                }
            }

        }

        // Return 0 if circuit cannot be fully connected with available connections
        return 0;
    }
}