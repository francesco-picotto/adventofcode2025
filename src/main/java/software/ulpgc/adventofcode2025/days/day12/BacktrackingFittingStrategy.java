package software.ulpgc.adventofcode2025.days.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BacktrackingFittingStrategy implements FittingStrategy {
    @Override
    public boolean canFit(int width, int height, List<Set<Shape>> variations) {
        return solve(width, height, new HashSet<>(), variations, 0);
    }

    private boolean solve(int w, int h, Set<Point> occupied, List<Set<Shape>> variations, int index) {
        if (index == variations.size()) return true;

        // Proviamo ogni variante della forma corrente (già normalizzata)
        for (Shape variant : variations.get(index)) {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (canPlace(variant, x, y, w, h, occupied)) {
                        Set<Point> placed = place(variant, x, y);
                        occupied.addAll(placed);
                        if (solve(w, h, occupied, variations, index + 1)) return true;
                        occupied.removeAll(placed);
                    }
                }
            }
        }
        return false;
    }

    private boolean canPlace(Shape s, int dx, int dy, int w, int h, Set<Point> occupied) {
        for (Point p : s.points()) {
            int tx = p.x() + dx;
            int ty = p.y() + dy;
            if (tx >= w || ty >= h || occupied.contains(new Point(tx, ty))) return false;
        }
        return true;
    }

    private Set<Point> place(Shape s, int dx, int dy) {
        return s.points().stream()
                .map(p -> new Point(p.x() + dx, p.y() + dy))
                .collect(java.util.stream.Collectors.toSet());
    }
}