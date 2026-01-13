package software.ulpgc.adventofcode2025.days.day08;
import java.util.*;
public class BasicCircuitAnalyzer implements CircuitAnalyzer{
    private static final int CONNECTION_LIMIT = 1000;

    @Override
    public long analyze(List<JunctionBox> boxes, List<Connection> allConnections) {

        UnionFind uf = new UnionFind(boxes.size());

        allConnections.stream()
                .limit(CONNECTION_LIMIT)
                .forEach(c -> uf.union(c.a().id(), c.b().id()));

        return boxes.stream()
                .map(box -> uf.find(box.id()))
                .distinct()
                .map(root -> uf.getSize(root))
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Integer::longValue)
                .reduce(1, (a, b) -> a*b);

    }
}
