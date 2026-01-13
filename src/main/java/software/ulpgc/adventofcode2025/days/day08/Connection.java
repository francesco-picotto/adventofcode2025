package software.ulpgc.adventofcode2025.days.day08;

public record Connection(JunctionBox a, JunctionBox b, double distance) implements Comparable<Connection>{
    @Override
    public int compareTo(Connection other){
        return Double.compare(this.distance, other.distance);
    }
}
