package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    private static boolean hasABBA(String input) {
        for (int i = 0; i < input.length() - 3; ++i)
            if (input.charAt(i) != input.charAt(i + 1) &&
                    input.charAt(i) == input.charAt(i + 3) &&
                    input.charAt(i + 1) == input.charAt(i + 2))
                return true;

        return false;
    }

    static boolean supportsTLS(String input) {
        Pattern pattern = Pattern.compile("\\[.*?]");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find())
            if (hasABBA(matcher.group().substring(1, matcher.group().length() - 1)))
                return false;

        return hasABBA(input.replaceAll("\\[.*?]", " "));
    }

    private static int solveFirst(String input) {
        int result = 0;
        for (String line : input.split("\n"))
            if (supportsTLS(line))
                ++result;

        return result;
    }

    private static Set<String> getABAs(String input) {
        Set<String> aba = new HashSet<>();
        for (int i = 0; i < input.length() - 2; ++i)
            if (input.charAt(i) == input.charAt(i + 2) && input.charAt(i) != input.charAt(i + 1))
                aba.add(String.valueOf(input.charAt(i + 1)) + input.charAt(i) + input.charAt(i + 1));

        return aba;
    }

    private static boolean hasBAB(Set<String> aba, String input) {
        for (int i = 0; i < input.length() - 2; ++i)
            if (input.charAt(i) == input.charAt(i + 2) && input.charAt(i) != input.charAt(i + 1) &&
                    aba.contains(input.substring(i, i + 3)))
                return true;

        return false;
    }

    static boolean supportsSSL(String input) {
        Pattern pattern = Pattern.compile("\\[.*?]");
        Set<String> aba = getABAs(input.replaceAll(pattern.pattern(), " "));

        Matcher matcher = pattern.matcher(input);
        while (matcher.find())
            if (hasBAB(aba, matcher.group().substring(1, matcher.group().length() - 1)))
                return true;

        return false;
    }

    private static int solveSecond(String input) {
        int result = 0;
        for (String line : input.split("\n"))
            if (supportsSSL(line))
                ++result;

        return result;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day7.class)));
        System.out.println(solveSecond(Input.get(Day7.class)));
    }
}
