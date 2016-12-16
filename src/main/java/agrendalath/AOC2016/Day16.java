package agrendalath.AOC2016;

import agrendalath.helpers.Input;

public class Day16 {
    private static String calculateChecksum(String input) {
        StringBuilder checksum = new StringBuilder();
        for (int i = 1; i < input.length(); i += 2)
            checksum.append(input.charAt(i - 1) == input.charAt(i) ? "1" : "0");

        return checksum.length() % 2 != 0 ? checksum.toString() : calculateChecksum(checksum.toString());
    }

    private static String generateData(String data) {
        return data + "0" +
                new StringBuilder(data).reverse().toString().replace('0', '2').replace('1', '0').replace('2', '1');
    }

    static String solveFirst(String input, int diskSize) {
        while (input.length() < diskSize)
            input = generateData(input);

        return calculateChecksum(input.substring(0, diskSize));
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day16.class), 272));
        System.out.println(solveFirst(Input.get(Day16.class), 35651584));
    }
}
