package software.ulpgc.adventofcode2025.day01;

import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day01.AdvancedStrategy;
import software.ulpgc.adventofcode2025.days.day01.BasicStrategy;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {
    @Test
    void testBasicStrategy() {
        BasicStrategy strategy = new BasicStrategy();
        // Parte dallo 0, muove a destra di 100: finisce su 0 -> 1 zero trovato
        assertEquals(1, strategy.countZeros(0, 100, 'R'));
        // Parte dallo 0, muove a destra di 50: finisce su 50 -> 0 zeri trovati
        assertEquals(0, strategy.countZeros(0, 50, 'R'));
        // Parte da 10, muove a sinistra di 10: finisce su 0 -> 1 zero trovato
        assertEquals(1, strategy.countZeros(10, 10, 'L'));
    }

    @Test
    void testAdvancedStrategy() {
        AdvancedStrategy strategy = new AdvancedStrategy();
        // Parte da 90, muove a destra di 20: passa per 0 -> 1 zero trovato
        assertEquals(1, strategy.countZeros(90, 20, 'R'));
        // Parte da 10, muove a sinistra di 20: passa per 0 -> 1 zero trovato
        assertEquals(1, strategy.countZeros(10, 20, 'L'));
        // Giro completo da 0 a 0: passa per lo zero una volta durante il movimento
        assertEquals(1, strategy.countZeros(0, 100, 'R'));
    }
}
