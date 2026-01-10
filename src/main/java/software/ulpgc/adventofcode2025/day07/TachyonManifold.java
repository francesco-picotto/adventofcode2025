package software.ulpgc.adventofcode2025.day07;
import java.util.*;

public class TachyonManifold{
    private final List<String> grid;

    public TachyonManifold(List<String> grid){
        this.grid = grid;
    }

    public char getAt(int row, int col){
        if(row < 0 || row >= grid.size() || col < 0 || col >= grid.get(row).length()) return ' ';
        return grid.get(row).charAt(col);
    }

    public int getStartColumn(){
        return grid.get(0).indexOf('S');
    }

    public int getHeight(){ return grid.size(); }

}
