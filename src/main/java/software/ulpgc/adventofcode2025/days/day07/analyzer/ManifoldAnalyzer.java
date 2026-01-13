package software.ulpgc.adventofcode2025.days.day07.analyzer;

import java.util.List;

/**
 * Strategy interface for analyzing tachyon beam behavior in manifold grids.
 *
 * Implementations of this interface define different approaches to simulating
 * and analyzing how beams travel through a manifold grid with splitters.
 * Different analyzers may track different metrics such as the number of splits
 * that occur or the total number of quantum timelines created.
 */
public interface ManifoldAnalyzer {
    /**
     * Analyzes the tachyon manifold grid and returns a numerical result.
     *
     * Simulates beam behavior through the manifold, where beams start at a
     * designated position and travel downward, potentially splitting when
     * encountering splitter symbols. The specific metric returned depends
     * on the implementation.
     *
     * @param grid List of strings representing the manifold grid, where each string is a row
     * @return The analysis result (e.g., number of splits or number of timelines)
     */
    long analyze(List<String> grid);
}