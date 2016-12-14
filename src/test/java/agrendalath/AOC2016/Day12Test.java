package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.*;

public class Day12Test {
    @Test
    public void solveFirst() throws Exception {
        String input = "cpy 41 a\ninc a\ninc a\ndec a\njnz a 2\ndec a";
        assertEquals("WA", 42, Day12.solveFirst(input));
    }

}
