package software.ulpgc.adventofcode2025.days.day09.analyzer;

import software.ulpgc.adventofcode2025.days.day09.domain.Tile;

import java.util.*;

/**
 * Strategy interface for analyzing a collection of tiles in a grid.
 * Implementations of this interface provide different algorithms for analyzing
 * spatial relationships and properties of tiles.
 */
public interface GridAnalyzer {

    /**
     * Analyzes a list of tiles and returns a numeric result based on the implementation's algorithm.
     * The specific meaning of the returned value depends on the concrete implementation.
     *
     * @param tiles the list of tiles to analyze
     * @return a long value representing the analysis result (interpretation varies by implementation)
     */
    long analyze(List<Tile> tiles);
}