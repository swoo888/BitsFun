package NumberLookup;

public class NumbersMap {
    private static final long MAX_NUMBER = 9999999999L;
    private BitsManager bitsManager;
    private NumberLookup anyUsedNumberLookup;
    private int[] data;

    public NumbersMap() {
        bitsManager = new BitsManager();
        data = new int[bitsManager.getArraySizeForNumber(MAX_NUMBER)];
        anyUsedNumberLookup = (new NumberLookup(data, bitsManager)).buildLookups();
    }

    /**
     * Constant time look up of the first number that matches boolean condition "used".
     *
     * @param used: type of number to get.  True for first number that is used.  False for first number that is not used.
     * @return char array of the first number that matches input param used.
     */
    public char[] get(boolean used) {
        return anyUsedNumberLookup.getNumberUsed(used);
    }

    /**
     * Constant time look up of the current status of a number.
     *
     * @param number: the number to search
     * @return true if number is used, else false
     */
    public boolean get(char[] number) {
        long n = parseNumber(number);
        return bitsManager.getUsedForNumber(data, n);
    }

    /**
     * Constant time to set a number's "used" status
     *
     * @param number: the number to set its status
     * @param used:   the new status for the number.  True to indicate a number is used, False for unused.
     */
    public void set(char[] number, boolean used) {
        long n = parseNumber(number);
        int pos = bitsManager.setUsedForNumber(data, n, used);
        anyUsedNumberLookup.updateLookups(pos, w -> w != 0);
    }

    private long parseNumber(char[] number) {
        long n;
        try {
            n = Long.parseLong(String.valueOf(number));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid number: %s", String.valueOf(number)));
        }
        validateNumberInRange(n);
        return n;
    }

    private void validateNumberInRange(long number) {
        if (number < 0 || number > MAX_NUMBER) {
            throw new IllegalArgumentException(String.format("Only supports number from 0 to %d", MAX_NUMBER));
        }
    }
}
