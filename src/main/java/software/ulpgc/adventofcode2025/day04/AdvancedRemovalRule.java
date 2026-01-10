package software.ulpgc.adventofcode2025.day04;

public class AdvancedRemovalRule extends BasicRemovalRule{
    @Override
    public int apply(char[][] grid) {
        int count = 0;
        int currentBatch;

        do{
            currentBatch = super.apply(grid);
            count += currentBatch;
        }while(currentBatch > 0);
        return count;
    }
}
