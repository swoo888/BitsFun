import NumberLookup.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        NumbersMap m = new NumbersMap();

        char[] d = "0000000000".toCharArray();
        assert (!m.get(d));
        m.set(d, true);
        assert (m.get(d));
        assert (Arrays.equals(m.get(true), d));
        m.set(d, false);
        assert (!m.get(d));

        d = "0000000099".toCharArray();
        assert (!m.get(d));
        m.set(d, true);
        assert (Arrays.equals(m.get(true), d));
        assert (m.get(d));
        m.set(d, false);
        assert (!m.get(d));

        char[] d9 = "9999999999".toCharArray();
        assert (!m.get(d9));
        m.set(d9, true);
        assert (Arrays.equals(m.get(true), d9));
        assert (m.get(d9));

        char[] d6 = "6666666699".toCharArray();
        assert (!m.get(d6));
        m.set(d6, true);
        assert (Arrays.equals(m.get(true), d6));
        assert (m.get(d6));

        char[] d3 = "3333333333".toCharArray();
        assert (!m.get(d3));
        m.set(d3, true);
        assert (Arrays.equals(m.get(true), d3));
        assert (m.get(d3));

        m.set(d3, false);
        assert (Arrays.equals(m.get(true), d6));
        m.set(d6, false);
        assert (Arrays.equals(m.get(true), d9));

        char[] d1 = "0000000010".toCharArray();
        assert (!m.get(d1));
        m.set(d1, true);
        assert (Arrays.equals(m.get(true), d1));
        assert (m.get(d1));
    }
}
