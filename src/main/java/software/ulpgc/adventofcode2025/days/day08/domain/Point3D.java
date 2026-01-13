package software.ulpgc.adventofcode2025.days.day08.domain;

/**
 * Represents a point in 3D space with x, y, and z coordinates.
 *
 * This record provides a simple representation of 3D coordinates and includes
 * a method to calculate Euclidean distance to another point. Being a record
 * ensures immutability and provides automatic implementations of equals,
 * hashCode, and toString based on the coordinate values.
 *
 * @param x The x-coordinate in 3D space
 * @param y The y-coordinate in 3D space
 * @param z The z-coordinate in 3D space
 */
public record Point3D(double x, double y, double z) {
    /**
     * Compact constructor for validation (currently empty but available for future constraints).
     */
    public Point3D {}

    /**
     * Calculates the Euclidean distance between this point and another point in 3D space.
     *
     * Uses the standard 3D distance formula:
     * distance = √((x₁-x₂)² + (y₁-y₂)² + (z₁-z₂)²)
     *
     * This distance is commonly used to determine the physical separation between
     * junction boxes and to sort connections by proximity.
     *
     * @param other The other point to calculate distance to
     * @return The Euclidean distance between this point and the other point
     */
    public double distance(Point3D other) {
        return Math.sqrt(Math.pow(x - other.x, 2) +
                Math.pow(y - other.y, 2) +
                Math.pow(z - other.z, 2));
    }
}