package software.ulpgc.adventofcode2025.day02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @Test
    void testSimpleRepeatRule() {
        IdRule rule = new SimpleRepeatRule();

        // Caso valido: 123123 -> lunghezza 6 (pari), parti uguali
        assertEquals(123123, rule.evaluate(123123));

        // Caso non valido: lunghezza pari ma parti diverse
        assertEquals(0, rule.evaluate(123456));

        // Caso non valido: lunghezza dispari
        assertEquals(0, rule.evaluate(123));
    }

    @Test
    void testMultipleRepeatRule() {
        InvalidRule rule = new MultipleRepeatRule();

        // Caso valido: p=3, "121212" (3 ripetizioni di "12")
        assertEquals(121212, rule.evaluate(121212));

        // Caso valido: p=2, "5555" (2 ripetizioni di "55")
        assertEquals(5555, rule.evaluate(5555));

        // Caso non valido: non divisibile per i primi o non ripetuto correttamente
        assertEquals(0, rule.evaluate(1234567));
    }
}