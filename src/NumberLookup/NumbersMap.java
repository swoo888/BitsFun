package NumberLookup;

public class NumbersMap {
    private static final long MAX_NUMBER = 9999999999L;
    private Helper helper;
    private NumberLookup anyUsedNumberLookup, allUsedNumberLookup;
    private int[] data;

    public NumbersMap() {
        helper = new Helper();
        NumberLookup lu = new NumberLookup();
        data = new int[helper.getArraySize(MAX_NUMBER)];
        anyUsedNumberLookup = lu.build(data, helper);
        allUsedNumberLookup = lu.build(data, helper);
    }

    /**
     * Constant time look up of the first number that matches boolean condition "used".
     *
     * @param used: type of number to get.  True for first number that is used.  False for first number that is not used.
     * @return char array of the first number that matches input param used.
     */
    public char[] get(boolean used) {
        if (used) {
            return anyUsedNumberLookup.getFirstNumber(true);
        } else {
            return allUsedNumberLookup.getFirstNumber(false);
        }
    }

    /**
     * Constant time look up of the current status of a number.
     *
     * @param number: the number to search
     * @return true if number is used, else false
     */
    public boolean get(char[] number) {
        long n = getNumber(number);
        return helper.getValue(data, n);
    }

    /**
     * Constant time to set a number's "used" status
     *
     * @param number: the number to set its status
     * @param used:   the new status for the number.  True to indicate a number is used, False for unused.
     */
    public void set(char[] number, boolean used) {
        long n = getNumber(number);
        int pos = helper.updateValue(data, n, used);
        anyUsedNumberLookup.update(pos, w -> w != 0);
        allUsedNumberLookup.update(pos, w -> w == helper.getWordOnes());
    }

    private long getNumber(char[] number) {
        long n;
        try {
            n = Long.parseLong(String.valueOf(number));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid number: %s", String.valueOf(number)));
        }
        checkNumber(n);
        return n;
    }

    private void checkNumber(long number) {
        if (number < 0 || number > MAX_NUMBER) {
            throw new IllegalArgumentException(String.format("Only supports number from 0 to %d", MAX_NUMBER));
        }
    }
}
