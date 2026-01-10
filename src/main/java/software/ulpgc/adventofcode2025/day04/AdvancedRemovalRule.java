package software.ulpgc.adventofcode2025.day04;

public class AdvancedRemovalRule implements RemovalRule{

    private final RemovalRule basicRule = new BasicRemovalRule();

    @Override
    public int apply(char[][] grid) {
        int count = 0;
        int currentBatch;

        while((currentBatch = basicRule.apply(grid)) > 0) {
            count += currentBatch;
        }
        return count;
    }
}
