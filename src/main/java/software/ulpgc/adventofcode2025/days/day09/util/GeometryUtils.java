package software.ulpgc.adventofcode2025.days.day09.util;

import software.ulpgc.adventofcode2025.days.day09.domain.Tile;

import java.util.List;

/**
 * Utility class providing geometric calculations for point-in-polygon operations.
 * This class uses the ray casting algorithm to determine if a point lies inside a polygon.
 */
public class GeometryUtils {

    /**
     * Determines whether a point is inside a polygon defined by a list of tiles.
     * This method first checks if the point lies on the polygon's perimeter, then uses
     * the ray casting algorithm to determine if the point is inside the polygon's interior.
     *
     * @param px the x-coordinate of the point to test
     * @param py the y-coordinate of the point to test
     * @param polygon the list of tiles defining the polygon vertices in order
     * @return true if the point is inside or on the perimeter of the polygon, false otherwise
     */
    public static boolean isPointInside(double px, double py, List<Tile> polygon) {
        // Check if the point is on one of the perimeter segments
        for (int i = 0; i < polygon.size(); i++) {
            if (isPointOnSegment(px, py, polygon.get(i), polygon.get((i + 1) % polygon.size()))) {
                return true;
            }
        }

        // Ray Casting Algorithm
        // Cast a horizontal ray from the point to infinity and count edge crossings
        // If the count is odd, the point is inside; if even, it's outside
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Tile pi = polygon.get(i);
            Tile pj = polygon.get(j);

            // Check if the ray crosses this edge
            if (((pi.y() > py) != (pj.y() > py)) &&
                    (px < (double) (pj.x() - pi.x()) * (py - pi.y()) / (pj.y() - pi.y()) + pi.x())) {
                inside = !inside;
            }
        }
        return inside;
    }

    /**
     * Checks if a point lies on a line segment defined by two tiles.
     * This method handles both horizontal and vertical segments with floating-point precision.
     * A small epsilon value (1e-9) is used for floating-point comparison tolerance.
     *
     * @param px the x-coordinate of the point to test
     * @param py the y-coordinate of the point to test
     * @param a the first endpoint of the segment
     * @param b the second endpoint of the segment
     * @return true if the point lies on the segment between points a and b, false otherwise
     */
    private static boolean isPointOnSegment(double px, double py, Tile a, Tile b) {
        if (a.x() == b.x()) {
            // Vertical segment: check if x matches and y is within range
            return Math.abs(px - a.x()) < 1e-9 &&
                    py >= Math.min(a.y(), b.y()) &&
                    py <= Math.max(a.y(), b.y());
        } else {
            // Horizontal segment: check if y matches and x is within range
            return Math.abs(py - a.y()) < 1e-9 &&
                    px >= Math.min(a.x(), b.x()) &&
                    px <= Math.max(a.x(), b.x());
        }
    }
}