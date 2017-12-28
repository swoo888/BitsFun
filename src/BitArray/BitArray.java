package BitArray;

import java.util.function.IntPredicate;

/***
 * A bit array big enough to hold number of bits for max number of items.  Each bit represents one number
 * from 0 to number.
 */
public class BitArray {
    private static final int WORD_ONES = 0xFFFFFFFF;
    private static final int WORD_SIZE = 32;
    private int size;
    private int[] data;  // the underlying data representation of bits

    /**
     * Instantiates a bit array large enough to hold boolean status for all numbers from 0 to number
     *
     * @param number: the largest number
     */
    public BitArray(long number) {
        size = (int) (number / WORD_SIZE) + (number % WORD_SIZE == 0 ? 0 : 1);
        data = new int[size];
    }

    public int getWordSize() {
        return WORD_SIZE;
    }

    public int getSize() {
        return size;
    }

    public boolean getPredicateValueForNumber(long number, IntPredicate predicate) {
        int pos = getWordPosForNumber(number);
        return predicate.test(data[pos]);
    }

    private int getWordPosForNumber(long number) {
        return (int) (number / WORD_SIZE);
    }

    private int getBitPosInWordForNumber(long number) {
        return (int) (number % WORD_SIZE);
    }

    /***
     * sets the value at position
     * @param position: the position or number index to set value
     * @param value: the value to set
     */
    public void setBitValue(long position, boolean value) {
        int wordPos = getWordPosForNumber(position);
        int bitPos = getBitPosInWordForNumber(position);
        int bitValue = 1 << bitPos;
        int word = data[wordPos];
        if (value) {
            word |= bitValue;
        } else {
            word &= WORD_ONES - bitValue;
        }
        data[wordPos] = word;
    }

    /***
     * gets the value at position or number
     * @param position: the number or position to get value
     * @return the value at position or number
     */
    public boolean getBitValue(long position) {
        int wordPos = getWordPosForNumber(position);
        int bitPos = getBitPosInWordForNumber(position);
        return (data[wordPos] & 1 << bitPos) != 0;
    }

    /***
     * Gets the first number position with value
     * @param value: True for used, False for unused
     * @param start: the start position
     * @param len: number of position to search
     * @return the first position found with value, or -1 if not found
     */
    public long getFirstPositionWithValue(boolean value, long start, int len) {
        for (long i = start; i < start + len; i++) {
            if (getBitValue(i) == value)
                return i;
        }
        return -1;
    }

}
