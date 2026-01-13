package software.ulpgc.adventofcode2025.day03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.adventofcode2025.days.day03.AdvancedBankRule;
import software.ulpgc.adventofcode2025.days.day03.BankRule;
import software.ulpgc.adventofcode2025.days.day03.SimpleBankRule;

import static org.junit.jupiter.api.Assertions.*;

class BankRuleTest {

    @Test
    @DisplayName("SimpleBankRule dovrebbe trovare i due massimi sequenziali")
    void testSimpleBankRule() {
        BankRule rule = new SimpleBankRule();
        // Con "1524":
        // 1. Max tra index 0-2 è '5' (index 1)
        // 2. Max tra index 2-3 è '4' (index 3)
        // Risultato: 54
        assertEquals(54, rule.evaluate("1524"));
    }

    @Test
    @DisplayName("AdvancedBankRule dovrebbe processare correttamente stringhe lunghe")
    void testAdvancedBankRule() {
        BankRule rule = new AdvancedBankRule();
        // Stringa di 12 caratteri (minimo per J_LENGTH)
        String input = "987654321012";
        assertDoesNotThrow(() -> rule.evaluate(input));

        long result = rule.evaluate("999999999999");
        assertEquals(999999999999L, result);
    }
}