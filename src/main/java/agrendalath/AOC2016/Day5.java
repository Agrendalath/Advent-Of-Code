package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import org.apache.commons.codec.digest.DigestUtils;

public class Day5 {
    static String solveFirst(String input) {
        StringBuilder code = new StringBuilder();

        for (int i = 0; ; ++i) {
            String md5Hex = DigestUtils.md5Hex(input + Integer.toString(i));
            if (md5Hex.indexOf("00000") == 0) {
                code.append(md5Hex.charAt(5));
                if (code.length() == 8)
                    return code.toString();
            }
        }
    }

    static String solveSecond(String input) {
        char[] code = new char[8];
        int guessed = 0;

        for (int i = 0; ; ++i) {
            String md5Hex = DigestUtils.md5Hex(input + Integer.toString(i));
            if (md5Hex.indexOf("00000") == 0 && md5Hex.charAt(5) < '8' && md5Hex.charAt(5) >= '0' &&
                    code[Character.getNumericValue(md5Hex.charAt(5))] == 0) {
                code[Character.getNumericValue(md5Hex.charAt(5))] = md5Hex.charAt(6);
                ++guessed;
                if (guessed == 8)
                    return new String(code);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day5.class)));
        System.out.println(solveSecond(Input.get(Day5.class)));
    }
}
