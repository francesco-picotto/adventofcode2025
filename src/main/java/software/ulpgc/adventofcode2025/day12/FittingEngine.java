package software.ulpgc.adventofcode2025.day12;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FittingEngine {
    private final int width;
    private final int height;

    public FittingEngine(int width, int height){
        this.width = width;
        this.height = height;
    }

    public boolean canFit(List<Shape> required) {
        // Ordina i pezzi dal più grande al più piccolo (euristica classica)
        List<Shape> sorted = required.stream()
                .sorted((a, b) -> Integer.compare(b.points().size(), a.points().size()))
                .toList();
        return solve(new HashSet<>(), sorted, 0);
    }

    private boolean solve(Set<Point> occupied, List<Shape> remaining, int index) {
        if (index == remaining.size()) return true;

        Shape baseShape = remaining.get(index);

        // Uso delle varianti pre-calcolate (idealmente passate o cacheate)
        for (Shape variant : baseShape.getVariations()) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (canPlace(variant, x, y, occupied)) {
                        Set<Point> placed = place(variant, x, y);
                        occupied.addAll(placed);
                        if (solve(occupied, remaining, index + 1)) return true;
                        occupied.removeAll(placed);
                    }
                }
            }
        }
        return false;
    }
    private boolean canPlace(Shape s, int dx, int dy, Set<Point> occupied) {
        for (Point p : s.points()) {
            Point t = p.translate(dx, dy);
            if (t.x() >= width || t.y() >= height || occupied.contains(t)) return false;
        }
        return true;
    }

    private Set<Point> place(Shape s, int dx, int dy) {
        return s.points().stream().map(p -> p.translate(dx, dy)).collect(Collectors.toSet());
    }
}
