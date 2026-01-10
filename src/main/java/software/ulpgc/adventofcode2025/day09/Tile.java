package software.ulpgc.adventofcode2025.day09;

public record Tile(int x, int y) {
    public long areaTo(Tile other){
        return (long) (Math.abs(this.x - other.x) + 1) * (Math.abs(this.y - other.y) + 1);
    }
}
