package software.ulpgc.adventofcode2025.days.day08.domain;

/**
 * Represents a junction box in the circuit positioned in 3D space.
 *
 * Each junction box has a unique identifier and a position in 3D space.
 * Junction boxes can be connected to form circuit components, and their
 * physical distance is used to determine connection costs. Being a record
 * provides immutability and automatic implementations of standard methods.
 *
 * @param id The unique identifier for this junction box (used for Union-Find indexing)
 * @param position The 3D coordinates of this junction box
 */
public record JunctionBox(int id, Point3D position) {
    /**
     * Calculates the physical distance between this junction box and another.
     *
     * Delegates to the Point3D distance calculation to determine the Euclidean
     * distance between the two boxes' positions. This distance is typically used
     * to sort connections and prioritize shorter connections when forming circuits.
     *
     * @param other The other junction box to calculate distance to
     * @return The Euclidean distance between the two junction boxes
     */
    public double distanceTo(JunctionBox other){
        return this.position.distance(other.position);
    }
}