package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 {
    private static int solveFirst(String input) {
        Pattern pattern = Pattern.compile("(\\d+)T.*?(\\d+)T.*?(\\d+)T");
        List<Node> list = new ArrayList<>();
        for (String line : input.split("\n")) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                continue;
            list.add(new Node(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))));
        }

        int viablePairs = 0;

        for (Node node : list)
            for (Node in : list)
                if (node.fitsIn(in))
                    ++viablePairs;

        return viablePairs;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day22.class)));
    }

    private static class Node {
        private final int size;
        private int used;
        private int available;

        Node(int used, int available) {
            size = used + available;
            this.used = used;
            this.available = available;
        }

        public int getSize() {
            return size;
        }

        int getUsed() {
            return used;
        }

        int getAvailable() {
            return available;
        }

        boolean fitsIn(Node in) {
            return this != in && used != 0 && used <= in.available;
        }

        boolean fitIn(Node in) {
            if (!fitsIn(in))
                return false;

            in.used += used;
            in.available -= used;
            used = 0;
            available = size;
            return true;
        }
    }
}
