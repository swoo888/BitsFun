import NumberLookup.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        NumbersMap m = new NumbersMap();

        String number = "0000000099";
        testNumber(m, number);

        number = "0000000000";
        testNumber(m, number);

        number = "6666666699";
        testNumber(m, number);

        char[] d3 = "3333333333".toCharArray();
        m.set(d3, true);
        assert (Arrays.equals(m.get(true), d3));
        assert (m.get(d3));

        char[] d6 = "6666666666".toCharArray();
        m.set(d6, true);

        assert (Arrays.equals(m.get(true), d3));
        m.set(d3, false);
        assert (Arrays.equals(m.get(true), d6));

        assert(Arrays.equals(m.get(false), "0000000000".toCharArray()));
        m.set("0000000000".toCharArray(), true);
        assert(Arrays.equals(m.get(false), "0000000001".toCharArray()));
    }

    private static void testNumber(NumbersMap m, String number) {
        char[] d = number.toCharArray();
        assert (!m.get(d));
        m.set(d, true);
        assert (m.get(d));
        assert (Arrays.equals(m.get(true), d));
        m.set(d, false);
        assert (!m.get(d));
    }
}
