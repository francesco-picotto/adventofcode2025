package software.ulpgc.adventofcode2025.day08;
import java.util.*;

public class AdvanceCircuitAnalyzer implements CircuitAnalyzer{
    @Override
    public long analyze(List<JunctionBox> boxes, List<Connection> allConnections) {
        UnionFind uf = new UnionFind(boxes.size());
        for(Connection c : allConnections){
            int rootA = uf.find(c.a().id());
            int rootB = uf.find(c.b().id());

            if(rootA != rootB){
                uf.union(rootA, rootB);

                if(uf.getComponentsCount() == 1){
                    return (long) c.a().x() * (long) c.b().x();
                }
            }

        }
        return 0;
    }
}
