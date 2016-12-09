package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day9Test {
    @Test
    public void solveFirst() throws Exception {
        assertEquals("WA0", 6, Day9.solveFirst("ADVENT"));
        assertEquals("WA1", 7, Day9.solveFirst("A(1x5)BC"));
        assertEquals("WA2", 9, Day9.solveFirst("(3x3)XYZ"));
        assertEquals("WA3", 11, Day9.solveFirst("A(2x2)BCD(2x2)EFG"));
        assertEquals("WA4", 6, Day9.solveFirst("(6x1)(1x3)A"));
        assertEquals("WA5", 18, Day9.solveFirst("X(8x2)(3x3)ABCY"));
    }

    @Test
    public void solveSecond() throws Exception {
        assertEquals("WA0", 9, Day9.solveSecond("(3x3)XYZ"));
        assertEquals("WA1", 20, Day9.solveSecond("X(8x2)(3x3)ABCY"));
        assertEquals("WA2", 241920, Day9.solveSecond("(27x12)(20x12)(13x14)(7x10)(1x12)A"));
        assertEquals("WA3", 445, Day9.solveSecond("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));
    }
}
