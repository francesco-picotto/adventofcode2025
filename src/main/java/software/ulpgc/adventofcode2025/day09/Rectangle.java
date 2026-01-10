package software.ulpgc.adventofcode2025.day09;

public record Rectangle(int xMin, int xMax, int yMin, int yMax) {
    public static Rectangle from(Tile t1, Tile t2) {
        return new Rectangle(
                Math.min(t1.x(), t2.x()),
                Math.max(t1.x(), t2.x()),
                Math.min(t1.y(), t2.y()),
                Math.max(t1.y(), t2.y())
        );
    }
    public long area() {
        return (long) (xMax - xMin + 1) * (yMax - yMin + 1);
    }

    public double centerX() { return (xMin + xMax) / 2.0; }
    public double centerY() { return (yMin + yMax) / 2.0; }

}
