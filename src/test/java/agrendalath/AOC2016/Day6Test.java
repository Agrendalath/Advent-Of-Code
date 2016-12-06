package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.*;

public class Day6Test {
    @Test
    public void solveFirst() {
        assertEquals("WA", "easter", Day6.solveFirst("eedadn\ndrvtee\neandsr\nraavrd\n" +
                "atevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar"));
    }

    @Test
    public void solveSecond() {
        assertEquals("WA", "advent", Day6.solveSecond("eedadn\ndrvtee\neandsr\nraavrd\n" +
                "atevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar"));
    }

}
