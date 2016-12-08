package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Day8Test {
    private final int SIZE_X = 7;
    private final int SIZE_Y = 3;


    @Test
    public void lightRect() throws Exception {
        boolean[][] expected = {
                {true, true, true, false, false, false, false},
                {true, true, true, false, false, false, false},
                {false, false, false, false, false, false, false},
        };
        boolean[][] display = new boolean[SIZE_Y][SIZE_X];
        assertEquals("WA0", 6, Day8.lightRect(display, 3, 2));
        assertArrayEquals("WA1", expected, display);
    }

    @Test
    public void rotateColumn() throws Exception {
        boolean[][] expected = {
                {true, false, true, false, false, false, false},
                {true, true, true, false, false, false, false},
                {false, true, false, false, false, false, false}
        };
        boolean[][] display = {
                {true, true, true, false, false, false, false},
                {true, true, true, false, false, false, false},
                {false, false, false, false, false, false, false}
        };
        Day8.rotateColumn(display, 1, 1);
        assertArrayEquals("WA", expected, display);
    }

    @Test
    public void rotateRow() throws Exception {
        boolean[][] expected = {
                {false, false, false, false, true, false, true},
                {true, true, true, false, false, false, false},
                {false, true, false, false, false, false, false}
        };
        boolean[][] display = {
                {true, false, true, false, false, false, false},
                {true, true, true, false, false, false, false},
                {false, true, false, false, false, false, false}
        };
        Day8.rotateRow(display, 0, 4);
        assertArrayEquals("WA", expected, display);
    }

    @Test
    public void rotateColumn2() throws Exception {
        boolean[][] expected = {
                {false, true, false, false, true, false, true},
                {true, false, true, false, false, false, false},
                {false, true, false, false, false, false, false}
        };
        boolean[][] display = {
                {false, false, false, false, true, false, true},
                {true, true, true, false, false, false, false},
                {false, true, false, false, false, false, false}
        };
        Day8.rotateColumn(display, 1, 1);
        assertArrayEquals("WA", expected, display);
    }

    @Test
    public void solveFirst() throws Exception {
        assertEquals("WA", 6, Day8.solveFirst("rect 3x2\nrotate column x=1 by 1\nrotate row y=0 by 4\n" +
                "rotate column x=1 by 1", SIZE_Y, SIZE_X));
    }

}
