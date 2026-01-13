package software.ulpgc.adventofcode2025.days.day08.domain;

/**
 * Represents a potential connection between two junction boxes with a distance metric.
 *
 * This record encapsulates a connection opportunity between two junction boxes,
 * storing both boxes and the distance between them. The connection implements
 * Comparable to enable sorting by distance, which is essential for algorithms
 * like Kruskal's Minimum Spanning Tree that process connections in order of cost.
 *
 * @param a The first junction box in the connection
 * @param b The second junction box in the connection
 * @param distance The physical distance between the two boxes (used as connection cost)
 */
public record Connection(JunctionBox a, JunctionBox b, double distance) implements Comparable<Connection>{
    /**
     * Compares this connection to another based on distance.
     *
     * Enables sorting of connections by their distance values, typically in
     * ascending order. This allows algorithms to process shorter (cheaper)
     * connections before longer ones, which is fundamental to greedy algorithms
     * like Kruskal's MST.
     *
     * @param other The other connection to compare to
     * @return A negative integer, zero, or positive integer as this connection's
     *         distance is less than, equal to, or greater than the other's distance
     */
    @Override
    public int compareTo(Connection other){
        return Double.compare(this.distance, other.distance);
    }
}