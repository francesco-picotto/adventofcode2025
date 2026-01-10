package software.ulpgc.adventofcode2025.day09;

import java.util.List;
import java.util.stream.IntStream;

public class LoopRectangleAnalyzer implements GridAnalyzer {
    @Override
    public long analyze(List<Tile> tiles) {
        if (tiles == null || tiles.size() < 2) return 0L;

        int n = tiles.size();
        // Utilizziamo parallel() per velocizzare il calcolo su grandi set di dati (O(N^3))
        return IntStream.range(0, tiles.size())
                .parallel()
                .mapToLong(i -> calculateMaxForTile(i, tiles))
                .max()
                .orElse(0L);
    }

    private long calculateMaxForTile(int i, List<Tile> tiles) {
        long localMax = 0;
        Tile t1 = tiles.get(i);
        for (int j = i + 1; j < tiles.size(); j++) {
            Rectangle rect = Rectangle.from(t1, tiles.get(j));
            long area = rect.area();

            if (area > localMax && isValid(rect, tiles)) {
                localMax = area;
            }
        }
        return localMax;
    }

    private boolean isValid(Rectangle rect, List<Tile> polygon) {
        if (!GeometryUtils.isPointInside(rect.centerX(), rect.centerY(), polygon)) return false;

        for (int i = 0; i < polygon.size(); i++) {
            if (isEdgeIntersectingRect(polygon.get(i), polygon.get((i + 1) % polygon.size()), rect)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEdgeIntersectingRect(Tile a, Tile b, Rectangle rect) {
        if (a.x() == b.x()) { // Segmento verticale
            return a.x() > rect.xMin() && a.x() < rect.xMax() &&
                    Math.max(a.y(), b.y()) > rect.yMin() && Math.min(a.y(), b.y()) < rect.yMax();
        } else { // Segmento orizzontale
            return a.y() > rect.yMin() && a.y() < rect.yMax() &&
                    Math.max(a.x(), b.x()) > rect.xMin() && Math.min(a.x(), b.x()) < rect.xMax();
        }
    }

}