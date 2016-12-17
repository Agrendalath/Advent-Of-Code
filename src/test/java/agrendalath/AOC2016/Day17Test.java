package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day17Test {
    @Test
    public void solveFirst() throws Exception {
        assertEquals("WA0", "DDRRRD", Day17.solveFirst("ihgpwlah"));
        assertEquals("WA1", "DDUDRLRRUDRD", Day17.solveFirst("kglvqrro"));
        assertEquals("WA2", "DRURDRUDDLLDLUURRDULRLDUUDDDRR", Day17.solveFirst("ulqzkmiv"));
    }

    @Test
    public void solveSecond() throws Exception {
        assertEquals("WA0", 370, Day17.solveSecond("ihgpwlah"));
        assertEquals("WA1", 492, Day17.solveSecond("kglvqrro"));
        assertEquals("WA2", 830, Day17.solveSecond("ulqzkmiv"));
    }
}
