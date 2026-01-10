package software.ulpgc.adventofcode2025.day04;

public class BasicRemovalRule implements RemovalRule {
    @Override
    public int apply(char[][] grid){
        if(grid.length == 0) return 0;
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] toRemove = new boolean[rows][cols];
        int count = 0;

        // 2. Prima passata: identifichiamo cosa rimuovere basandoci sullo stato attuale
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (shouldRemove(grid, i, j)) {
                    toRemove[i][j] = true;
                    count++;
                }
            }
        }

        // 3. Seconda passata: applichiamo le modifiche alla griglia originale
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (toRemove[i][j]) {
                    grid[i][j] = 'X';
                }
            }
        }

        return count;
    }



    protected boolean shouldRemove(char[][] grid, int row, int col) {
        if(grid[row][col] != '@') return false;
        return countNeighbours(grid, row, col) < 4;
    }

    private int countNeighbours(char[][] grid, int row, int col) {
        int count = 0;
        for(int i = row - 1; i <= row + 1; i++) {
            if(i >= 0 && i < grid.length){
                for(int j = col - 1; j <= col + 1; j++) {
                    if(j >= 0 && j < grid[0].length){
                        if(grid[i][j] == '@' && (i != row || j != col)) count++;
                    }
                }
            }
        }
        return count;
    }
}
