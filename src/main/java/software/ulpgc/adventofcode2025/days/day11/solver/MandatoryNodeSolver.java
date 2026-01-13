package software.ulpgc.adventofcode2025.days.day11.solver;

import software.ulpgc.adventofcode2025.days.day11.domain.ReactorMap;

import java.util.*;

/**
 * Solver implementation that calculates paths through a reactor network with mandatory
 * intermediate nodes that must be visited in a specific order.
 *
 * This solver evaluates two possible routing chains through critical nodes ("svr", "fft", "dac")
 * and returns the maximum number of paths achievable through either chain. This is useful
 * for determining optimal routing through systems with required checkpoint nodes.
 */
public class MandatoryNodeSolver implements ReactorSolver {

    /**
     * Solves the reactor puzzle by evaluating two possible path chains through mandatory nodes.
     *
     * The two chains evaluated are:
     * - Chain A: svr → fft → dac → out
     * - Chain B: svr → dac → fft → out
     *
     * For each chain, the total path count is the product of path counts between consecutive
     * nodes. The solver returns the maximum of the two chains, representing the best routing
     * option through the mandatory nodes.
     *
     * @param map the reactor map containing the network structure
     * @return the maximum number of paths achievable through either mandatory node chain,
     *         or 0 if any segment in both chains is unreachable
     */
    @Override
    public long solve(ReactorMap map) {
        // Calculate paths for chain A: svr → fft → dac → out
        long pathA = calculateChain(map, "svr", "fft", "dac", "out");

        // Calculate paths for chain B: svr → dac → fft → out
        long pathB = calculateChain(map, "svr", "dac", "fft", "out");

        // Return the better of the two routing options
        return Math.max(pathA, pathB);
    }

    /**
     * Calculates the total number of paths through a chain of mandatory nodes.
     *
     * This method computes the product of path counts between each consecutive pair
     * of nodes in the chain. If any segment has zero paths (unreachable), the entire
     * chain is considered unreachable and returns 0.
     *
     * For example, with nodes [A, B, C, D]:
     * - Segment 1: paths from A to B
     * - Segment 2: paths from B to C
     * - Segment 3: paths from C to D
     * - Total: segment1 × segment2 × segment3
     *
     * This multiplicative approach accounts for all possible combinations of paths
     * through the mandatory sequence.
     *
     * @param map the reactor map containing the network structure
     * @param nodes a varargs array of node names representing the mandatory chain sequence
     * @return the total number of distinct paths through the entire chain,
     *         or 0 if any segment is unreachable
     */
    private long calculateChain(ReactorMap map, String... nodes) {
        long total = 1L;

        // Iterate through consecutive pairs of nodes in the chain
        for (int i = 0; i < nodes.length - 1; i++) {
            // Count paths between this pair of consecutive nodes
            long segment = map.countPaths(nodes[i], nodes[i + 1], new HashMap<>());

            // If this segment is unreachable, the entire chain fails
            if (segment == 0) return 0;

            // Multiply by the number of paths in this segment
            total *= segment;
        }
        return total;
    }
}