package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class Day19 {
    static int solveFirst(int input) {
        Queue<Integer> elves = new ArrayDeque<>();
        for (int i = 1; i <= input; ++i)
            elves.add(i);

        while (true) {
            int firstElf = elves.remove();
            if (elves.isEmpty())
                return firstElf;
            elves.remove();
            elves.add(firstElf);
        }
    }

    static int solveSecond(int input) {
        Deque<Integer> first = new ArrayDeque<>();
        Deque<Integer> second = new ArrayDeque<>();
        for (int i = 1; i <= input; ++i) {
            if (i <= input / 2)
                first.addLast(i);
            else
                second.addFirst(i);
        }

        while (!first.isEmpty() && !second.isEmpty()) {
            if (first.size() > second.size())
                first.removeLast();
            else
                second.removeLast();

            second.addFirst(first.removeFirst());
            first.addLast(second.removeLast());
        }

        return first.remove();
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Integer.parseInt(Input.get(Day19.class))));
        System.out.println(solveSecond(Integer.parseInt(Input.get(Day19.class))));
    }
}
