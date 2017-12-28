package NumberLookup;

import BitArray.BitArray;

import java.util.ArrayList;
import java.util.function.IntPredicate;

/***
 * A lookup class for a BitArray.  It uses layers of BitArray each containing values of status of its higher layer.
 * for example: each position at BitArray at layer 1 will contain the status value for position 0 to 31
 * at BitArray layer 0.
 */
public class NumberLookup {
    /***
     * ArrayList of BitArray to build a constant time lookup layers for numbers. When we try to look up
     * a number of certain value, we will search from bottum of the list.
     */
    ArrayList<BitArray> numberBitsLookup = new ArrayList<>();

    public NumberLookup(BitArray numberBits) {
        buildLookups(numberBits);
    }

    /***
     * Builds a lookup layer for containt time lookup of values
     * @param numberBits: the BitArray that we are  building a lookup layer
     */
    private void buildLookups(BitArray numberBits) {
        BitArray lookupBits = numberBits;
        while (true) {
            numberBitsLookup.add(lookupBits);
            int size = lookupBits.getSize();
            if (size <= 1) {
                break;
            }
            lookupBits = new BitArray(size);
        }
    }

    /***
     * Updates the lookup layers when a number value is changed
     * @param number: the number whose value was changed
     * @param lookupUpdatePredicate: a predicate to get the next layer value
     */
    public void updateLookups(long number, IntPredicate lookupUpdatePredicate) {
        int size = numberBitsLookup.size();
        // loop through list, each upper one updates the one below.  so the last one is size-2, which updates
        // item at size-1
        for (int i = 0; i <= size - 2; i++) {
            BitArray numberBits = numberBitsLookup.get(i);
            boolean value = numberBits.getPredicateValueForNumber(number, lookupUpdatePredicate);
            int numberNext = (int) (number / numberBits.getWordSize());
            BitArray numberBitsNext = numberBitsLookup.get(i + 1);
            numberBitsNext.setBitValue(numberNext, value);
            number = numberNext;
        }
    }

    /***
     * Gets the first number that has the value "used"
     * @param used: the value type of number to get.  True for used number, False for unused number
     * @return char[] of first number found, or null if no number has value "used"
     */
    public char[] getFirstNumberUsed(boolean used) {
        int size = numberBitsLookup.size();
        long number = 0;
        for (int i = size - 1; i >= 0; i--) {
            BitArray numberBits = numberBitsLookup.get(i);
            number = number * numberBits.getWordSize();
            number = numberBits.getFirstPositionWithValue(used, number, numberBits.getWordSize());
            if (number < 0) {
                return null;
            }
        }
        return String.format("%010d", number).toCharArray();
    }
}
