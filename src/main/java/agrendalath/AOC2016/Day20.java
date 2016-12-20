package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import com.google.common.primitives.UnsignedInteger;

public class Day20 {
    private static final long MAX_UNSIGNED_INT = UnsignedInteger.MAX_VALUE.longValue();

    private static ImmutableRangeSet<Long> getRanges(String input) {
        RangeSet<Long> ranges = TreeRangeSet.create();

        for (String line : input.split("\n")) {
            String[] pair = line.split("-");
            ranges.add(Range.closed(Long.parseLong(pair[0]), Long.parseLong(pair[1])));
        }

        return ImmutableRangeSet.copyOf(ranges);
    }

    static long solveFirst(String input) {
        ImmutableRangeSet<Long> ranges = getRanges(input);
        long last = 0;
        for (Range<Long> range : ranges.asRanges()) {
            if (last < range.lowerEndpoint())
                return last;

            last = range.upperEndpoint() + 1;
        }

        if (last < MAX_UNSIGNED_INT)
            return last;

        return -1;
    }

    static long solveSecond(String input) {
        ImmutableRangeSet<Long> ranges = getRanges(input);
        long addresses = 0;
        long last = 0;
        for (Range<Long> range : ranges.asRanges()) {
            addresses += (range.lowerEndpoint() - last);
            last = range.upperEndpoint() + 1;
        }

        if (last < MAX_UNSIGNED_INT)
            addresses += MAX_UNSIGNED_INT - last;

        return addresses;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day20.class)));
        System.out.println(solveSecond(Input.get(Day20.class)));
    }
}
