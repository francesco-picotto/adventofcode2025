package software.ulpgc.adventofcode2025.days.day08;

import java.util.List;

public interface CircuitAnalyzer {
    long analyze(List<JunctionBox> boxes, List<Connection> allConnections);
}
