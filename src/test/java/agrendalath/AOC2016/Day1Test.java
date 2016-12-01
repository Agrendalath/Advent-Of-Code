package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.*;

public class Day1Test {
    @Test
    public void exampleTests1() {
        assertEquals("WA", 5, Day1.solveFirst("R2, L3"));
        assertEquals("WA", 2, Day1.solveFirst("R2, R2, R2"));
        assertEquals("WA", 12, Day1.solveFirst("R5, L5, R5, R3"));
    }

    @Test
    public void exampleTests2() {
        assertEquals("WA", 4, Day1.solveSecond("R8, R4, R4, R8"));
        assertEquals("WA", 11, Day1.solveSecond("R8, R2, L2, L2, R1, R1, R4"));
    }
}
