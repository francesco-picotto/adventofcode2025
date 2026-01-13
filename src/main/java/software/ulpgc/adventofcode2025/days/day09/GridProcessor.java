package software.ulpgc.adventofcode2025.days.day09;

import java.util.List;

public class GridProcessor {
    private final GridAnalyzer analyzer;

    public GridProcessor(GridAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public long solve(List<Tile> tiles) {
        return analyzer.analyze(tiles);
    }
}
