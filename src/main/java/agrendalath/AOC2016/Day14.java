package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {


    private static String generateKey(String input) {
        return DigestUtils.md5Hex(input);
    }

    private static String generateStretchedKey(String input) {
        String md5Hex = input;
        for (int i = 0; i <= 2016; ++i)
            md5Hex = DigestUtils.md5Hex(md5Hex);
        return md5Hex;
    }

    private static int solve(String input, Function<String, String> generator) {
        Pattern triplet = Pattern.compile("(.)\\1\\1");
        Pattern quintet = Pattern.compile("(.)\\1\\1\\1\\1");

        List<PadKey> possibleKeys = new ArrayList<>();
        List<PadKey> quintetKeys = new ArrayList<>();
        List<Integer> keys = new ArrayList<>();

        for (int index = 0; keys.size() < 65; ++index) {
            if (index % 1000 == 0) {
                for (int i = 0; i < possibleKeys.size(); ++i) {
                    for (int j = 0; j < quintetKeys.size(); ++j) {
                        if (quintetKeys.get(j).getIndex() > possibleKeys.get(i).getIndex() + 1000)
                            break;

                        if (quintetKeys.get(j).getIndex() <= possibleKeys.get(i).getIndex()) {
                            quintetKeys.remove(j);
                            --j;
                            continue;
                        }
                        if (quintetKeys.get(j).getCharacter() == possibleKeys.get(i).getCharacter()) {
                            keys.add(possibleKeys.get(i).getIndex());
                            possibleKeys.remove(i);
                            --i;
                        }
                    }
                }
            }

            String md5Hex = generator.apply(input + index);
            Matcher matchTriplets = triplet.matcher(md5Hex);
            if (!matchTriplets.find())
                continue;

            possibleKeys.add(new PadKey(index, md5Hex.charAt(matchTriplets.start())));

            Matcher matchQuintets = quintet.matcher(md5Hex);
            while (matchQuintets.find())
                quintetKeys.add(new PadKey(index, md5Hex.charAt(matchQuintets.start())));

        }
        Collections.sort(keys);
        return keys.get(63);
    }

    static int solveFirst(String input) {
        return solve(input, Day14::generateKey);
    }

    static int solveSecond(String input) {
        return solve(input, Day14::generateStretchedKey);
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day14.class)));
        System.out.println(solveSecond(Input.get(Day14.class)));
    }

    private static class PadKey {
        private final int index;
        private final char character;


        private PadKey(int index, char character) {
            this.index = index;
            this.character = character;
        }

        int getIndex() {
            return index;
        }

        char getCharacter() {
            return character;
        }
    }
}
