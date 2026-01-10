package software.ulpgc.adventofcode2025.day08;

public record Point3D(double x, double y, double z) {
    public Point3D {}


    public double distance(Point3D other) {
        return Math.sqrt(Math.pow(x - other.x, 2) +
                Math.pow(y - other.y, 2) +
                Math.pow(z - other.z, 2));
    }
}
