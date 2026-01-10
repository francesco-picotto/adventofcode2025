package software.ulpgc.adventofcode2025.day08;

public record JunctionBox(int id, double x, double y, double z) {
    public double distanceTo(JunctionBox other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) +
                         Math.pow(this.y - other.y, 2) +
                         Math.pow(this.z - other.z, 2));
    }
}
