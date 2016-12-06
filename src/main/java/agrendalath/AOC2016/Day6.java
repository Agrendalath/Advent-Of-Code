package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;

public class Day6 {
    private static List<Multiset<Character>> buildMultiset(String input) {
        List<Multiset<Character>> letters = new ArrayList<>();

        for (String line : input.split("\n")) {
            for (int i = 0; i < line.length(); ++i) {
                if (letters.size() <= i)
                    letters.add(HashMultiset.create());

                letters.get(i).add(line.charAt(i));
            }
        }

        return letters;
    }

    static String solveFirst(String input) {
        StringBuilder code = new StringBuilder();
        List<Multiset<Character>> letters = buildMultiset(input);


        for (Multiset<Character> set : letters) {
            int max = -1;
            char mostFrequentLetter = 0;
            for (char c = 'a'; c <= 'z'; ++c) {
                if (set.count(c) > max) {
                    max = set.count(c);
                    mostFrequentLetter = c;
                }
            }
            code.append(Character.toString(mostFrequentLetter));
        }

        return code.toString();
    }

    static String solveSecond(String input) {
        StringBuilder code = new StringBuilder();
        List<Multiset<Character>> letters = buildMultiset(input);

        for (Multiset<Character> set : letters) {
            int min = Integer.MAX_VALUE;
            char leastFrequentLetter = 0;
            for (char c = 'a'; c <= 'z'; ++c) {
                if (set.count(c) != 0 && set.count(c) < min) {
                    min = set.count(c);
                    leastFrequentLetter = c;
                }
            }
            code.append(Character.toString(leastFrequentLetter));
        }

        return code.toString();
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day6.class)));
        System.out.println(solveSecond(Input.get(Day6.class)));
    }
}
