package software.ulpgc.adventofcode2025.days.day07;

import java.util.List;

public class ManifoldProcessor {
    private final ManifoldAnalyzer analyzer;

    public ManifoldProcessor(ManifoldAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public long solve(List<String> grid) {
        return analyzer.analyze(grid);
    }
}