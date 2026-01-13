package software.ulpgc.adventofcode2025.day05;

import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day05.IngredientRange;
import software.ulpgc.adventofcode2025.days.day05.InventoryAnalyzer;
import software.ulpgc.adventofcode2025.days.day05.StockFreshnessChecker;
import software.ulpgc.adventofcode2025.days.day05.TotalFreshCapacityEstimator;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InventoryAnalyzerTest {

    @Test
    void testStockFreshnessChecker() {
        InventoryAnalyzer checker = new StockFreshnessChecker();

        List<String> ranges = List.of("10-20", "30-40");
        List<String> ids = List.of("15", "25", "30", "45");

        // 15 è nel primo range, 30 è nel secondo. Totale: 2
        assertEquals(2, checker.analyze(ranges, ids));
    }

    @Test
    void testTotalFreshCapacityEstimator_OverlappingRanges() {
        InventoryAnalyzer estimator = new TotalFreshCapacityEstimator();

        // Intervalli: 10-20 e 15-25 si sovrappongono -> diventano 10-25 (dimensione 16)
        // L'intervallo 30-31 è staccato (dimensione 2)
        // Totale atteso: 18
        List<String> ranges = List.of("10-20", "15-25", "30-31");

        assertEquals(18, estimator.analyze(ranges, List.of()));
    }

    @Test
    void testTotalFreshCapacityEstimator_ConsecutiveRanges() {
        InventoryAnalyzer estimator = new TotalFreshCapacityEstimator();

        // 10-20 e 21-30 sono consecutivi. Il tuo codice (current.end() + 1)
        // dovrebbe unirli in un unico range 10-30 (dimensione 21)
        List<String> ranges = List.of("10-20", "21-30");

        assertEquals(21, estimator.analyze(ranges, List.of()));
    }

    @Test
    void testEmptyInput() {
        InventoryAnalyzer checker = new StockFreshnessChecker();
        InventoryAnalyzer estimator = new TotalFreshCapacityEstimator();

        assertEquals(0, checker.analyze(List.of(), List.of()));
        assertEquals(0, estimator.analyze(List.of(), List.of()));
    }

    @Test
    void testIngredientRangeContains() {
        IngredientRange range = new IngredientRange(10, 20);
        assertTrue(range.contains(10), "Dovrebbe contenere l'estremo inferiore");
        assertTrue(range.contains(20), "Dovrebbe contenere l'estremo superiore");
        assertFalse(range.contains(9), "Non dovrebbe contenere numeri fuori range");
    }
}
