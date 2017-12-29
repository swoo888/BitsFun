package NumberLookup;

import BitArray.BitArray;
import BitArray.BitArrays;

public class NumbersMap {
    private static final long MAX_NUMBER = 9999999999L;
    private NumberLookup usedNumberLookups;
    private NumberLookup unusedNumberLookups;
    private BitArray numberBitsArray;

    public NumbersMap() {
        numberBitsArray = new BitArrays(MAX_NUMBER);
        usedNumberLookups = new NumberLookups(numberBitsArray);
        unusedNumberLookups = new NumberLookups(numberBitsArray);
    }

    /**
     * Constant time look up of the first number that has value "used"
     *
     * @param used: true for used number, false for unused number
     * @return char array of the first number that matches condition "used"
     */
    public char[] get(boolean used) {
        if (used)
            return usedNumberLookups.getFirstNumberUsed(true);
        return unusedNumberLookups.getFirstNumberUsed(false);
    }

    /**
     * Constant time look up of the current value of a number.
     *
     * @param number: the number of interest
     * @return true if number is used, else false
     */
    public boolean get(char[] number) {
        long n = parseNumber(number);
        return numberBitsArray.getBitValue(n);
    }

    /**
     * Sets the number value to used
     *
     * @param number: the number of interest
     * @param used:   True for used, False for unused
     */
    public void set(char[] number, boolean used) {
        long n = parseNumber(number);
        numberBitsArray.setBitValue(n, used);
        usedNumberLookups.updateLookups(n, this::anyValueUsed);
        unusedNumberLookups.updateLookups(n, this::allValuesUsed);
    }

    private boolean anyValueUsed(boolean[] values) {
        for (boolean b : values) {
            if (b) {
                return true;
            }
        }
        return false;
    }

    private boolean allValuesUsed(boolean[] values) {
        for (boolean b : values) {
            if (!b) {
                return false;
            }
        }
        return true;
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
