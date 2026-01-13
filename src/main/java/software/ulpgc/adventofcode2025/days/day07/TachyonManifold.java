package software.ulpgc.adventofcode2025.days.day07;
import java.util.*;

public class TachyonManifold{
    public static final char SPLITTER = '^';
    public static final char START = 'S';
    private final List<String> grid;
    private final int startColumn;

    public TachyonManifold(List<String> grid){
        if (grid == null || grid.isEmpty()) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }
        this.grid = grid;
        this.startColumn = grid.get(0).indexOf(START);
        if (this.startColumn == -1) {
            throw new IllegalStateException("'S' starting column is not found");
        }
    }

    public char getAt(int row, int col) {
        if (isOutOfBounds(row, col)) return ' ';
        return grid.get(row).charAt(col);
    }

    public boolean isSplitter(int row, int col) {
        return getAt(row, col) == SPLITTER;
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= grid.size() || col < 0 || col >= grid.get(0).length();
    }


    public int getStartColumn(){return startColumn;}
    public int getHeight(){ return grid.size(); }
    public int getWidth(){ return grid.get(0).length(); }

}
