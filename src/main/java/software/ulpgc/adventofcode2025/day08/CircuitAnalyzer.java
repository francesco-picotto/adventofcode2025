package software.ulpgc.adventofcode2025.day08;

import java.util.List;

public interface CircuitAnalyzer {
    long analyze(List<JunctionBox> boxes, List<Connection> allConnections);
}
