package software.ulpgc.adventofcode2025.day09;

public record Tile(int x, int y) {
    public int distanceX(Tile other){ return Math.abs(this.x - other.x)+1; }
    public int distanceY(Tile other) { return Math.abs(this.y - other.y) + 1; }
}
