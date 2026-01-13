package software.ulpgc.adventofcode2025.days.day08;

public class CircuitProcessor {
    private final CircuitAnalyzer analyzer;

    public CircuitProcessor(CircuitAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public long solve(Day08Data data) {
        return analyzer.analyze(data.boxes(), data.connections());
    }
}