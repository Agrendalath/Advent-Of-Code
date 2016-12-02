package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day2Test {
    @Test
    public void solveFirst() {
        //noinspection SpellCheckingInspection
        assertEquals("WA", "1985", Day2.solveFirst("ULL\nRRDDD\nLURDL\nUUUUD"));
    }

    @Test
    public void solveSecond() {
        //noinspection SpellCheckingInspection
        assertEquals("WA", "5DB3", Day2.solveSecond("ULL\nRRDDD\nLURDL\nUUUUD"));
    }

}
