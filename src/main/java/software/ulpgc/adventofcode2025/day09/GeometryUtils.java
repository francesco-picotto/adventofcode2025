package software.ulpgc.adventofcode2025.day09;

import java.util.List;

public class GeometryUtils {
    public static boolean isPointInside(double px, double py, List<Tile> polygon) {
        // Check if the point is on one of the perimeter segment
        for (int i = 0; i < polygon.size(); i++) {
            if (isPointOnSegment(px, py, polygon.get(i), polygon.get((i + 1) % polygon.size()))) {
                return true;
            }
        }

        // Ray Casting Algorithm
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Tile pi = polygon.get(i);
            Tile pj = polygon.get(j);

            if (((pi.y() > py) != (pj.y() > py)) &&
                    (px < (double) (pj.x() - pi.x()) * (py - pi.y()) / (pj.y() - pi.y()) + pi.x())) {
                inside = !inside;
            }
        }
        return inside;
    }

    private static boolean isPointOnSegment(double px, double py, Tile a, Tile b) {
        if (a.x() == b.x()) {
            return Math.abs(px - a.x()) < 1e-9 && py >= Math.min(a.y(), b.y()) && py <= Math.max(a.y(), b.y());
        } else {
            return Math.abs(py - a.y()) < 1e-9 && px >= Math.min(a.x(), b.x()) && px <= Math.max(a.x(), b.x());
        }
    }
}