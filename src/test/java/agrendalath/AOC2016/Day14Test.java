package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day14Test {
    @Test
    public void solveFirst() throws Exception {
        assertEquals(22728, Day14.solveFirst("abc"));
    }

    @Test
    public void actualFirst() throws Exception {
        assertEquals(16106, Day14.solveFirst(Input.get(Day14.class)));
    }

    @Test
    public void solveSecond() throws Exception {
        assertEquals(22551, Day14.solveSecond("abc"));
    }

    @Test
    public void actualSecond() throws Exception {
        assertEquals(22423, Day14.solveSecond(Input.get(Day14.class)));
    }
}
