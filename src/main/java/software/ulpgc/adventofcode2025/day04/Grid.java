package software.ulpgc.adventofcode2025.day04;

public class Grid {
    private final char[][] cells;
    private final int rows;
    private final int cols;

    public Grid(char[][] cells) {
        this.cells = cells;
        this.rows = cells.length;
        this.cols = rows > 0 ? cells[0].length : 0;
    }

    public int rows() { return rows; }
    public int cols() { return cols; }

    public char get(int r, int c) {
        if (!isValid(r, c)) return ' ';
        return cells[r][c];
    }

    public void set(int r, int c, char value) {
        if (isValid(r, c)) cells[r][c] = value;
    }

    public boolean is(int r, int c, char value) {
        return get(r, c) == value;
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public int countNeighbors(int row, int col, char target) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) continue;
                if (is(i, j, target)) count++;
            }
        }
        return count;
    }
}
