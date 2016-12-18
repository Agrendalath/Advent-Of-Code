package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.*;

public class Day18Test {
    @Test
    public void solveFirst() throws Exception {
        assertEquals("WA0", 6, Day18.solveFirst("..^^.", 3));
        assertEquals("WA0", 38, Day18.solveFirst(".^^.^.^^^^", 10));
    }

}
