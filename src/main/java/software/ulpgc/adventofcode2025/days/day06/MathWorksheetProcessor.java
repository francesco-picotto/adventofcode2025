package software.ulpgc.adventofcode2025.days.day06;

import java.util.List;

public class MathWorksheetProcessor {
    private final MathWorksheetAnalyzer analyzer;

    public MathWorksheetProcessor(MathWorksheetAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public long solve(List<String> input) {
        return analyzer.analyze(input);
    }
}