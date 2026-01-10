package software.ulpgc.adventofcode2025.day09;

import java.util.List;
import java.util.stream.IntStream;

public class LoopRectangleAnalyzer implements GridAnalyzer {
    @Override
    public long analyze(List<Tile> tiles) {
        if (tiles == null || tiles.size() < 2) return 0L;

        int n = tiles.size();
        // Utilizziamo parallel() per velocizzare il calcolo su grandi set di dati (O(N^3))
        return IntStream.range(0, n)
                .parallel()
                .mapToLong(i -> {
                    long localMax = 0;
                    Tile t1 = tiles.get(i);
                    for (int j = i + 1; j < n; j++) {
                        Tile t2 = tiles.get(j);
                        long area = t1.areaTo(t2);

                        // Ottimizzazione: verifica la validità solo se l'area è maggiore del massimo trovato finora
                        if (area > localMax && isRectangleValid(t1, t2, tiles)) {
                            localMax = area;
                        }
                    }
                    return localMax;
                })
                .max()
                .orElse(0L);
    }

    private boolean isRectangleValid(Tile t1, Tile t2, List<Tile> polygon) {
        int xMin = Math.min(t1.x(), t2.x());
        int xMax = Math.max(t1.x(), t2.x());
        int yMin = Math.min(t1.y(), t2.y());
        int yMax = Math.max(t1.y(), t2.y());

        // 1. Controllo dell'interno: il centro del rettangolo deve essere nel poligono.
        // Se il rettangolo è una linea (es. xMin=xMax), il centro cade sul bordo, gestito da isPointInside.
        if (!isPointInside((xMin + xMax) / 2.0, (yMin + yMax) / 2.0, polygon)) {
            return false;
        }

        // 2. Controllo delle intersezioni: nessun bordo del poligono deve tagliare l'interno del rettangolo.
        // L'interno è l'insieme di punti (x, y) tali che xMin < x < xMax e yMin < y < yMax.
        for (int i = 0; i < polygon.size(); i++) {
            Tile a = polygon.get(i);
            Tile b = polygon.get((i + 1) % polygon.size());

            if (a.x() == b.x()) { // Segmento verticale del poligono
                int xEdge = a.x();
                // Se il segmento è strettamente dentro l'intervallo X del rettangolo
                if (xEdge > xMin && xEdge < xMax) {
                    int yLow = Math.min(a.y(), b.y());
                    int yHigh = Math.max(a.y(), b.y());
                    // Verifica se il segmento y del bordo si sovrappone all'interno y del rettangolo
                    if (yHigh > yMin && yLow < yMax) return false;
                }
            } else if (a.y() == b.y()) { // Segmento orizzontale del poligono
                int yEdge = a.y();
                // Se il segmento è strettamente dentro l'intervallo Y del rettangolo
                if (yEdge > yMin && yEdge < yMax) {
                    int xLow = Math.min(a.x(), b.x());
                    int xHigh = Math.max(a.x(), b.x());
                    // Verifica se il segmento x del bordo si sovrappone all'interno x del rettangolo
                    if (xHigh > xMin && xLow < xMax) return false;
                }
            }
        }

        return true;
    }

    private boolean isPointInside(double px, double py, List<Tile> polygon) {
        // Verifica se il punto è sul perimetro (Bordo = Valido)
        for (int i = 0; i < polygon.size(); i++) {
            Tile a = polygon.get(i);
            Tile b = polygon.get((i + 1) % polygon.size());
            if (isPointOnSegment(px, py, a, b)) return true;
        }

        // Algoritmo Ray Casting per determinare se il punto è interno
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            int xi = polygon.get(i).x(), yi = polygon.get(i).y();
            int xj = polygon.get(j).x(), yj = polygon.get(j).y();

            if (((yi > py) != (yj > py)) &&
                    (px < (double) (xj - xi) * (py - yi) / (yj - yi) + xi)) {
                inside = !inside;
            }
        }
        return inside;
    }

    private boolean isPointOnSegment(double px, double py, Tile a, Tile b) {
        // Dato che i segmenti sono ortogonali, semplifichiamo il controllo
        if (a.x() == b.x()) {
            return Math.abs(px - a.x()) < 1e-9 && py >= Math.min(a.y(), b.y()) && py <= Math.max(a.y(), b.y());
        } else {
            return Math.abs(py - a.y()) < 1e-9 && px >= Math.min(a.x(), b.x()) && px <= Math.max(a.x(), b.x());
        }
    }
}