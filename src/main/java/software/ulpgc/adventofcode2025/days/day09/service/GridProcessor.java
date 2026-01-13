package software.ulpgc.adventofcode2025.days.day09.service;

import software.ulpgc.adventofcode2025.days.day09.domain.Tile;
import software.ulpgc.adventofcode2025.days.day09.analyzer.GridAnalyzer;

import java.util.List;

/**
 * Service class that processes a grid of tiles using a specified analysis strategy.
 * This class follows the Strategy pattern, delegating the actual analysis logic
 * to a GridAnalyzer implementation.
 */
public class GridProcessor {
    private final GridAnalyzer analyzer;

    /**
     * Constructs a GridProcessor with a specific analysis strategy.
     *
     * @param analyzer the GridAnalyzer implementation to use for tile analysis
     */
    public GridProcessor(GridAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * Processes a list of tiles using the configured analyzer and returns the result.
     * This method delegates to the analyzer's analyze method.
     *
     * @param tiles the list of tiles to process
     * @return the numeric result of the analysis
     */
    public long solve(List<Tile> tiles) {
        return analyzer.analyze(tiles);
    }
}