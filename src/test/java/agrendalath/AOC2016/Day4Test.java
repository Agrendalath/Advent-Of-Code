package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("SpellCheckingInspection")
public class Day4Test {
    @Test
    public void isRealRoom() {
        assertTrue("WA", Day4.isRealRoom("aaaaa-bbb-z-y-x-123[abxyz]"));
        assertTrue("WA", Day4.isRealRoom("a-b-c-d-e-f-g-h-987[abcde]"));
        assertTrue("WA", Day4.isRealRoom("not-a-real-room-404[oarel]"));
        assertFalse("WA", Day4.isRealRoom("totally-real-room-200[decoy]"));
    }

    @Test
    public void solveFirst() {
        assertEquals("WA", 1514, Day4.solveFirst("aaaaa-bbb-z-y-x-123[abxyz]\n" +
                "a-b-c-d-e-f-g-h-987[abcde]\nnot-a-real-room-404[oarel]\ntotally-real-room-200[decoy]"));
    }
}
