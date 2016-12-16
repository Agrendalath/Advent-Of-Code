package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day16Test {
    @Test
    public void solveFirst() throws Exception {
        assertEquals("WA0", "100", Day16.solveFirst("110010110100", 12));
        assertEquals("WA1", "01100", Day16.solveFirst("10000", 20));
    }
}
