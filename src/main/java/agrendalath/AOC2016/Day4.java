package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.Arrays;

public class Day4 {
    static boolean isRealRoom(String room) {
        Occurrence[] occurrences = new Occurrence[26];
        for (int i = 0; i < 26; ++i)
            occurrences[i] = new Occurrence((char) ('a' + i));

        for (char c : room.toCharArray()) {
            if (c == '[')
                break;
            if (Character.isLowerCase(c))
                occurrences[c - 'a'].incrementOccurrences();
        }

        Arrays.sort(occurrences);
        String checksum = room.substring(room.indexOf("[") + 1, room.length() - 1);
        for (int i = 0; i < checksum.length(); ++i)
            if (checksum.charAt(i) != occurrences[i].getCharacter())
                return false;

        return true;
    }

    static int solveFirst(String input) {
        int result = 0;
        for (String line : input.split("\n"))
            if (isRealRoom(line))
                result += Integer.parseInt(line.replaceAll("\\D+", ""));

        return result;
    }

    private static int solveSecond(String input) {
        for (String line : input.split("\n")) {
            int rotation = Integer.parseInt(line.replaceAll("\\D+", "")) % 26;
            char[] name = line.substring(0, line.indexOf(line.replaceAll("\\D+", "")))
                    .replaceAll("-", " ").replaceAll(" ", "").toCharArray();

            for (int i = 0; i < name.length; ++i) {
                name[i] += rotation;
                if (name[i] > 'z')
                    name[i] = (char) (name[i] % 'z' + 'a' - 1);
            }
            //noinspection SpellCheckingInspection
            if (new String(name).contains("northpole"))
                return Integer.parseInt(line.replaceAll("\\D+", ""));
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day4.class)));
        System.out.println(solveSecond(Input.get(Day4.class)));
    }

    private static class Occurrence implements Comparable<Occurrence> {
        private final char character;
        private int occurrences;

        private Occurrence(char character) {
            this.character = character;
        }

        char getCharacter() {
            return character;
        }

        void incrementOccurrences() {
            ++occurrences;
        }

        @Override
        public int compareTo(Occurrence occurrence) {
            if (occurrences != occurrence.occurrences)
                return Integer.compare(occurrence.occurrences, occurrences);

            return Character.compare(character, occurrence.character);
        }
    }
}
