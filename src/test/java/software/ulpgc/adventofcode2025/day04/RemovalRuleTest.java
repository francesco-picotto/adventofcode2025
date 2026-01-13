package software.ulpgc.adventofcode2025.day04;

import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day04.AdvancedRemovalRule;
import software.ulpgc.adventofcode2025.days.day04.BasicRemovalRule;
import software.ulpgc.adventofcode2025.days.day04.Grid;
import software.ulpgc.adventofcode2025.days.day04.RemovalRule;

import static org.junit.jupiter.api.Assertions.*;

class RemovalRuleTest {

    @Test
    void should_count_neighbors_correctly_in_grid() {
        char[][] cells = {
                {'@', '@', '.'},
                {'@', '.', '.'},
                {'.', '.', '.'}
        };
        Grid grid = new Grid(cells);

        // Il punto (0,0) ha 2 vicini '@'
        assertEquals(2, grid.countNeighbors(0, 0, '@'));
        // Il punto (1,1) ha 3 vicini '@'
        assertEquals(3, grid.countNeighbors(1, 1, '@'));
    }

    @Test
    void basic_removal_rule_should_remove_at_with_less_than_4_neighbors() {
        // Griglia 3x3 piena di @: ogni cella ha meno di 4 vicini tranne il centro
        // Ma negli angoli e bordi verranno rimossi tutti in BasicRemovalRule
        char[][] cells = {
                {'@', '@', '@'},
                {'@', '@', '@'},
                {'@', '@', '@'}
        };

        RemovalRule rule = new BasicRemovalRule();
        int removed = rule.apply(cells);

        // Il centro (1,1) ha 8 vicini, non dovrebbe essere rimosso
        // Gli altri hanno 3 o 5 vicini.
        // Angoli (0,0): 3 vicini -> rimosso
        // Bordi (0,1): 5 vicini -> NON rimosso
        assertEquals(4, removed); // I 4 angoli vengono rimossi
        assertEquals('X', cells[0][0]);
        assertEquals('@', cells[1][1]);
    }

    @Test
    void advanced_removal_rule_should_cascade_removals() {
        // Una riga di @. In Basic verrebbero rimossi solo i bordi.
        // In Advanced, la rimozione dei bordi indebolisce i vicini finché spariscono tutti.
        char[][] cells = {
                {'@', '@', '@', '@', '@'}
        };

        RemovalRule rule = new AdvancedRemovalRule();
        int totalRemoved = rule.apply(cells);

        assertEquals(5, totalRemoved);
        for (char[] row : cells) {
            for (char cell : row) {
                assertEquals('X', cell);
            }
        }
    }

    @Test
    void should_handle_empty_grid() {
        char[][] emptyCells = new char[0][0];
        RemovalRule rule = new AdvancedRemovalRule();

        assertDoesNotThrow(() -> {
            int removed = rule.apply(emptyCells);
            assertEquals(0, removed);
        });
    }
}