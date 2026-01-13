package software.ulpgc.adventofcode2025.days.day12;

public record Point(int x, int y) {
    public Point rotate(){ return new Point(2 - y, x);}
    public Point flip() { return new Point (2-x, y); }
    public Point translate(int dx, int dy){
        return new Point(x + dx, y + dy);
    }
}
