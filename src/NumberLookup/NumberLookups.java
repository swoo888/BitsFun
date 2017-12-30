package NumberLookup;

import BitArray.BitArray;

import java.util.ArrayList;
import java.util.function.Predicate;

/***
 * A lookup class for a BitArrays.  It uses layers of BitArrays each containing values of status of its higher layer.
 * for example: each position at BitArrays at layer 1 will contain the status value for position 0 to 31
 * at BitArrays layer 0.
 */
public class NumberLookups implements NumberLookup {
    /***
     * ArrayList of BitArrays to build a constant time lookup layers for numbers. When we try to look up
     * a number of certain value, we will search from bottum of the list.
     */
    ArrayList<BitArray> numberBitsLookup = new ArrayList<>();
    private static final int LAYER_REDUCTION_SIZE = 32;

    public NumberLookups(BitArray numberBits) {
        buildLookups(numberBits);
    }

    /***
     * Builds a lookup layer for containt time lookup of values
     * @param numberBits: the BitArrays that we are  building a lookup layer
     */
    private void buildLookups(BitArray numberBits) {
        BitArray lookupBits = numberBits;
        while (true) {
            numberBitsLookup.add(lookupBits);
            int numberOfItems = lookupBits.getNumberOfItems(lookupBits.getSize(), LAYER_REDUCTION_SIZE);
            if (numberOfItems <= 1) {
                break;
            }
            lookupBits = lookupBits.newBitArray(numberOfItems);
        }
    }

    /***
     * Updates the lookup layers when a number value is changed
     * @param number: the number whose value was changed
     * @param lookupUpdatePredicate: a predicate to get the next layer value
     */
    @Override
    public void updateLookups(long number, Predicate<boolean[]> lookupUpdatePredicate) {
        int size = numberBitsLookup.size();
        // loop through list, each upper one updates the one below.  so the last one is size-2, which updates
        // item at size-1
        for (int i = 0; i <= size - 2; i++) {
            BitArray numberBits = numberBitsLookup.get(i);
            // gets the begining position of a number
            // for example number 33 with LAYER_REDUCTION_SIZE of 32, would be in position range: 32 to 63.
            long pos = (number / LAYER_REDUCTION_SIZE) * LAYER_REDUCTION_SIZE;
            boolean[] values = numberBits.getValuesInRange(pos, LAYER_REDUCTION_SIZE);
            boolean value = lookupUpdatePredicate.test(values);
            int numberNext = (int) (number / LAYER_REDUCTION_SIZE);
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
    @Override
    public char[] getFirstNumberUsed(boolean used) {
        int size = numberBitsLookup.size();
        long number = 0;
        for (int i = size - 1; i >= 0; i--) {
            BitArray numberBits = numberBitsLookup.get(i);
            number = number * LAYER_REDUCTION_SIZE;
            number = numberBits.getFirstPositionWithValue(used, number, LAYER_REDUCTION_SIZE);
            if (number < 0) {
                return null;
            }
        }
        return String.format("%010d", number).toCharArray();
    }
}
