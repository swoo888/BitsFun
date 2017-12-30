package BitArray;

/***
 * A bit array big enough to hold number of bits for max number of items.  Each bit represents one number
 * from 0 to number.
 */
public class BitArrays implements BitArray {
    private static final int WORD_ONES = 0xFFFFFFFF;
    private static final int WORD_SIZE = 32;
    private long size;
    private int[] data;  // the underlying data representation of bits

    /***
     * Creates a new BitArray to hold number of items
     * @param number: number of items
     * @return BitArray object
     */
    @Override
    public BitArray newBitArray(long number) {
        return new BitArrays(number);
    }

    /**
     * Instantiates a bit array large enough to hold boolean status for all numbers from 0 to number
     *
     * @param number: the largest number
     */
    public BitArrays(long number) {
        this.size = number;
        int numOfWords = getNumberOfItems(number, WORD_SIZE);
        data = new int[numOfWords];
    }

    /***
     * Gets the number of items for a number if each item is itemSize
     * @param number: the number to calculate the size
     * @param itemSize: each item size
     * @return number of items that is large enough for number if each item is of itemSize.
     */
    @Override
    public int getNumberOfItems(long number, int itemSize) {
        return (int) (number / itemSize) + (number % itemSize == 0 ? 0 : 1);
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public boolean[] getValuesInRange(long position, int count) {
        boolean[] values = new boolean[count];
        for (long i = position; i < position + count; i++) {
            values[(int) (i - position)] = getBitValue(i);
        }
        return values;
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
    @Override
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
    @Override
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
    @Override
    public long getFirstPositionWithValue(boolean value, long start, int len) {
        for (long i = start; i < start + len; i++) {
            if (getBitValue(i) == value)
                return i;
        }
        return -1;
    }

}
