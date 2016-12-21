package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("SpellCheckingInspection")
public class Day21Test {
    @Test
    public void solveFirst() throws Exception {
        String input = "swap position 4 with position 0\nswap letter d with letter b\nreverse positions 0 through 4\n" +
                "rotate left 1 step\nmove position 1 to position 4\nmove position 3 to position 0\n" +
                "rotate based on position of letter b\nrotate based on position of letter d";
        assertEquals("decab", Day21.solveFirst(input, "abcde"));
    }

    @Test
    public void swapPositions() throws Exception {
        String initial = "abcde";
        StringBuilder result = new StringBuilder(initial);
        Day21.swap("swap position 4 with position 0", result);
        assertEquals("ebcda", result.toString());
        Day21.swap("swap position 4 with position 0", result);
        assertEquals(initial, result.toString());
    }

    @Test
    public void swapLetters() throws Exception {
        String initial = "ebcda";
        StringBuilder result = new StringBuilder(initial);
        Day21.swap("swap letter d with letter b", result);
        assertEquals("edcba", result.toString());
        Day21.swap("swap letter d with letter b", result);
        assertEquals(initial, result.toString());
    }

    @Test
    public void rotateLeftBySteps() throws Exception {
        String initial = "abcde";
        StringBuilder result = new StringBuilder(initial);
        Day21.rotate("rotate left 1 step", result);
        assertEquals("bcdea", result.toString());
        Day21.rotate("rotate right 1 step", result);
        assertEquals(initial, result.toString());
    }

    @Test
    public void rotateRightBySteps() throws Exception {
        String initial = "abcde";
        StringBuilder result = new StringBuilder(initial);
        Day21.rotate("rotate right 12 steps", result);
        assertEquals("deabc", result.toString());
        Day21.rotate("rotate left 12 steps", result);
        assertEquals(initial, result.toString());
    }

    @Test
    public void rotateBasingOnPosition() throws Exception {
        StringBuilder result = new StringBuilder("abdec");
        Day21.rotate("rotate based on position of letter b", result);
        assertEquals("ecabd", result.toString());

        result = new StringBuilder("ecabd");
        Day21.rotate("rotate based on position of letter d", result);
        assertEquals("decab", result.toString());

        result = new StringBuilder("ecabd");
        Day21.rotate("rotate based on position of letter a", result);
        assertEquals("abdec", result.toString());

        result = new StringBuilder("ecabd");
        Day21.rotate("rotate based on position of letter d", result);
        assertEquals("decab", result.toString());
    }

    @Test
    public void generateRotations() throws Exception {
        int size = 8;
        int[] expected = {7, 0, 4, 1, 5, 2, 6, 3};
        assertArrayEquals(expected, Day21.generateRotations(size));
    }

    @Test
    public void rotateBackBasingOnPosition() throws Exception {
        String initial = "ecabd";
        int[] rotations = Day21.generateRotations(initial.length());
        StringBuilder result = new StringBuilder(initial);
        Day21.rotate("rotate based on position of letter b", result);
        Day21.rotateBack("rotate based on position of letter b", result, rotations);
        assertEquals(initial, result.toString());

        initial = "ecabd";
        rotations = Day21.generateRotations(initial.length());
        result = new StringBuilder(initial);
        Day21.rotate("rotate based on position of letter d", result);
        Day21.rotateBack("rotate based on position of letter d", result, rotations);
        assertEquals(initial, result.toString());

        initial = "decab";
        rotations = Day21.generateRotations(initial.length());
        result = new StringBuilder(initial);
        Day21.rotate("rotate based on position of letter d", result);
        Day21.rotateBack("rotate based on position of letter d", result, rotations);
        assertEquals(initial, result.toString());


        initial = "asdfnioq";
        rotations = Day21.generateRotations(initial.length());
        result = new StringBuilder(initial);
        Day21.rotate("rotate based on position of letter d", result);
        Day21.rotateBack("rotate based on position of letter d", result, rotations);
        assertEquals(initial, result.toString());
    }

    @Test
    public void reverse() throws Exception {
        String initial = "edcba";
        StringBuilder result = new StringBuilder(initial);
        Day21.reverse("reverse positions 0 through 4", result);
        assertEquals("abcde", result.toString());
        Day21.reverse("reverse positions 0 through 4", result);
        assertEquals(initial, result.toString());
    }

    @Test
    public void move() throws Exception {
        StringBuilder result = new StringBuilder("bcdea");
        Day21.move("move position 1 to position 4", result);
        assertEquals("bdeac", result.toString());

        result = new StringBuilder("bdeac");
        Day21.move("move position 3 to position 0", result);
        assertEquals("abdec", result.toString());
    }

    @Test
    public void moveBack() throws Exception {
        String initial = "bcdea";
        StringBuilder result = new StringBuilder(initial);
        Day21.move("move position 1 to position 4", result);
        Day21.moveBack("move position 1 to position 4", result);
        assertEquals(initial, result.toString());

        initial = "abdec";
        result = new StringBuilder(initial);
        Day21.move("move position 3 to position 0", result);
        Day21.moveBack("move position 3 to position 0", result);
        assertEquals(initial, result.toString());
    }

    @Test
    public void testSecond() throws Exception {
        assertNotEquals("eadghbfc", Day21.solveSecond(Input.get(Day21.class), "fbgdceah"));
        assertNotEquals("dahbfecg", Day21.solveSecond(Input.get(Day21.class), "fbgdceah"));
    }
}
