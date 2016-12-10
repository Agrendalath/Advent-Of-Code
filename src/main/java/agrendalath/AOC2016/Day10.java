package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static int executeInstruction(String instruction, Map<Integer, Bot> bots, Map<Integer, Integer> outputs, Pair wanted) {
        int result = -1;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(instruction);

        if (instruction.startsWith("value")) {
            matcher.find();
            int value = Integer.parseInt(matcher.group());
            matcher.find();
            int bot = Integer.parseInt(matcher.group());
            bots.putIfAbsent(bot, new Bot());

            if (bots.get(bot).isFull()) {
                bots.get(bot).addInstruction(instruction);
                return result;
            }
            bots.get(bot).removeInstruction(instruction);

            String reply = bots.get(bot).add(value);
            if (bots.get(bot).isWanted(wanted))
                result = bot;

            if (reply != null)
                result = Math.max(result, executeInstruction(reply, bots, outputs, wanted));

            return result;
        }
        Pattern botOrOutputPattern = Pattern.compile("bot|output");
        Matcher botOrOutput = botOrOutputPattern.matcher(instruction);
        botOrOutput.find();
        botOrOutput.find();
        boolean lowBot = botOrOutput.group().equals("bot");
        botOrOutput.find();
        boolean highBot = botOrOutput.group().equals("bot");

        matcher.find();
        int giver = Integer.parseInt(matcher.group());
        matcher.find();
        int lowReceiver = Integer.parseInt(matcher.group());
        matcher.find();
        int highReceiver = Integer.parseInt(matcher.group());

        bots.putIfAbsent(giver, new Bot());

        if (!bots.get(giver).isFull()) {
            bots.get(giver).addInstruction(instruction);
            return result;
        }
        bots.get(giver).removeInstruction(instruction);

        Pair pair = bots.get(giver).getMicrochips();
        String reply1 = null;
        String reply2 = null;

        if (lowBot) {
            bots.putIfAbsent(lowReceiver, new Bot());
            reply1 = bots.get(lowReceiver).add(pair.getLow());
            if (bots.get(lowReceiver).isWanted(wanted))
                result = lowReceiver;
        } else
            outputs.put(lowReceiver, pair.getLow());

        if (highBot) {
            bots.putIfAbsent(highReceiver, new Bot());
            reply2 = bots.get(highReceiver).add(pair.getHigh());
            if (bots.get(highReceiver).isWanted(wanted))
                result = Math.max(result, highReceiver);
        } else
            outputs.put(highReceiver, pair.getHigh());

        if (reply1 != null)
            result = Math.max(result, executeInstruction(reply1, bots, outputs, wanted));
        if (reply2 != null)
            result = Math.max(result, executeInstruction(reply2, bots, outputs, wanted));

        return result;
    }


    static int solveFirst(String input, Pair wanted) {
        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Integer> outputs = new HashMap<>();

        for (String line : input.split("\n")) {
            int output = executeInstruction(line, bots, outputs, wanted);
            if (output != -1)
                return output;
        }
        return -1;
    }

    static int solveSecond(String input, Pair wanted) {
        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Integer> outputs = new HashMap<>();
        int result = 1;

        for (String line : input.split("\n"))
            executeInstruction(line, bots, outputs, wanted);

        for (int i = 0; i < 3; ++i)
            result *= outputs.getOrDefault(i, 1);

        return result;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day10.class), new Pair(61, 17)));
        System.out.println(solveSecond(Input.get(Day10.class), new Pair(61, 17)));
    }

    private static class Bot {
        private final Pair microchips = new Pair();
        private final Queue<String> queue = new ArrayDeque<>();

        boolean isFull() {
            return microchips.isFull();
        }

        String add(int value) {
            return microchips.add(value) ? queue.poll() : null;
        }

        Pair getMicrochips() {
            Pair result = new Pair(microchips);
            microchips.clear();
            return result;
        }

        void addInstruction(String instruction) {
            queue.add(instruction);
        }

        void removeInstruction(String instruction) {
            if (!queue.isEmpty() && queue.peek().equals(instruction))
                queue.remove();
        }

        boolean isWanted(Pair pair) {
            return microchips.equals(pair);
        }
    }

    static class Pair {
        private Integer low;
        private Integer high;

        Pair() {

        }

        Pair(Pair pair) {
            low = pair.low;
            high = pair.high;
        }

        Pair(Integer low, Integer high) {
            this.low = low;
            this.high = high;
        }

        boolean add(int value) {
            if (low == null) {
                low = value;
                return false;
            }
            high = value;
            if (low.compareTo(high) > 0) {
                Integer temp = low;
                low = high;
                high = temp;
            }
            return true;
        }

        boolean isFull() {
            return low != null && high != null;
        }

        void clear() {
            low = null;
            high = null;
        }

        Integer getLow() {
            return low;
        }

        Integer getHigh() {
            return high;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Pair pair = (Pair) o;

            return !(low == null || high == null || pair.low == null || pair.high == null) &&
                    ((low.equals(pair.low) && high.equals(pair.high)) ||
                            (high.equals(pair.low) && low.equals(pair.high)));
        }

        @Override
        public int hashCode() {
            int result = low != null ? low.hashCode() : 0;
            result = 31 * result + (high != null ? high.hashCode() : 0);
            return result;
        }
    }
}
