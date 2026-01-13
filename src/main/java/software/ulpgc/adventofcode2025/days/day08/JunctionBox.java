package software.ulpgc.adventofcode2025.days.day08;

public record JunctionBox(int id, Point3D position) {
    public double distanceTo(JunctionBox other){
        return this.position.distance(other.position);
    }
}
