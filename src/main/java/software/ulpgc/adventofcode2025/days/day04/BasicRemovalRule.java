package software.ulpgc.adventofcode2025.days.day04;

public class BasicRemovalRule implements RemovalRule {
    @Override
    public int apply(char[][] cells){
        Grid grid = new Grid(cells);
        boolean[][] toRemove = new boolean[grid.rows()][grid.cols()];
        int count = 0;

        // 2. Prima passata: identifichiamo cosa rimuovere basandoci sullo stato attuale
        for (int i = 0; i < grid.rows(); i++) {
            for (int j = 0; j < grid.cols(); j++) {
                if (shouldRemove(grid, i, j)) {
                    toRemove[i][j] = true;
                    count++;
                }
            }
        }
        applyRemovals(grid, toRemove);
        return count;
    }



    protected boolean shouldRemove(Grid grid, int r, int c) {
        return grid.is(r, c, '@') && grid.countNeighbors(r, c, '@') < 4;
    }

    private void applyRemovals(Grid grid, boolean[][] toRemove) {
        for(int i = 0; i < grid.rows(); i++) {
            for(int j = 0; j < grid.cols(); j++) {
                if(toRemove[i][j]) grid.set(i, j, 'X');
            }
        }
    }
}
