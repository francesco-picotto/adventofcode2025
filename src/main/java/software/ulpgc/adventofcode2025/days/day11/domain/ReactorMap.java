package software.ulpgc.adventofcode2025.days.day11.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Represents a directed graph structure modeling a reactor's connection network.
 * This immutable record encapsulates the topology of nodes and their connections,
 * providing utilities for graph traversal and path counting.
 *
 * The reactor map is used to calculate the number of distinct paths between nodes,
 * which is essential for determining routing options within the reactor system.
 */
public record ReactorMap(Map<String, Set<String>> connections) {

    /**
     * Retrieves all neighboring nodes connected from a given node.
     * Returns an empty set if the node has no outgoing connections or doesn't exist in the map.
     *
     * @param node the source node to query
     * @return a set of node names that are directly reachable from the source node
     */
    public Set<String> getNeighbours(String node) {
        return connections.getOrDefault(node, Collections.emptySet());
    }

    /**
     * Counts the total number of distinct paths from a start node to an end node
     * using dynamic programming with memoization to optimize performance.
     *
     * This method uses a recursive depth-first approach where:
     * - Base case: if start equals end, there is exactly 1 path (the trivial path)
     * - Recursive case: the total paths equal the sum of paths from all neighbors
     *
     * The memoization map caches results for each node to avoid redundant computations,
     * which is particularly important in graphs with shared subpaths or cycles.
     *
     * Time complexity: O(V + E) with memoization, where V is vertices and E is edges.
     *
     * @param start the starting node name
     * @param end the target node name
     * @param memo a memoization map storing previously computed path counts from each node
     * @return the total number of distinct paths from start to end
     */
    public long countPaths(String start, String end, Map<String, Long> memo) {
        // Base case: we've reached the destination
        if (start.equals(end)) return 1L;

        // Return cached result if this node's path count was already computed
        if (memo.containsKey(start)) return memo.get(start);

        // Sum the path counts from all neighboring nodes
        long paths = getNeighbours(start).stream()
                .mapToLong(n -> countPaths(n, end, memo))
                .sum();

        // Cache the result for this node to avoid recomputation
        memo.put(start, paths);
        return paths;
    }
}