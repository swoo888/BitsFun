package NumberLookup;

/**
 * The BitsManager class manages bit array of used values for numbers.  It handles all internal bit calculation
 * and modifications.
 * It knows the bit position of any long number.
 * It can calculate the long number from a bit position.
 * It can calculate upper or lower layer position.
 */
public class BitsManager {
    private static final int WORD_ONES = 0xFFFFFFFF;
    private static final int WORD_SIZE = 32;

    private int getWordPosForNumber(long number) {
        return (int) (number / WORD_SIZE);
    }

    private int getBitPosInWordForNumber(long number) {
        return (int) (number % WORD_SIZE);
    }

    private int getFirstBitPosUsedInWord(int word, boolean used) {
        if (used) {
            if (word == WORD_ONES)
                return 0;
            if (word == 0)
                return -1;
            int pos = 0;
            while ((word & 1) == 0) {
                pos++;
                word = word >> 1;
            }
            return pos;
        } else {
            return getFirstBitPosUsedInWord(~word, true);
        }
    }

    /**
     * calculates the smallest array size to hold all bits for a long number
     *
     * @param number: the number for size calculation
     * @return the minimal number of int to hold all bit representation
     */
    public int getArraySizeForNumber(long number) {
        return (int) (number / WORD_SIZE) + (number % WORD_SIZE == 0 ? 0 : 1);
    }

    /***
     * gets the upper layer pos for a used value
     * @param pos: the current word position
     * @param data: the data bits array
     * @param used: boolean value, true to find used value, false to find unused value
     * @return int position of the upper layer
     */
    public int getUpperLayerPosUsed(int pos, int[] data, boolean used) {
        int word = data[pos];
        int bitPos;
        bitPos = getFirstBitPosUsedInWord(word, used);
        if (bitPos < 0) {
            return -1;
        }
        return pos * WORD_SIZE + bitPos;
    }

    /***
     * sets the used value of a number
     * @param data: the data bit array
     * @param number: the number whose value is to be set
     * @param used: true to set value to used, false to set it unused
     * @return the int position that was updated
     */
    public int setUsedForNumber(int[] data, long number, boolean used) {
        int wordPos = getWordPosForNumber(number);
        int bitPos = getBitPosInWordForNumber(number);
        int bitValue = 1 << bitPos;
        int word = data[wordPos];
        if (used) {
            word |= bitValue;
        } else {
            word &= WORD_ONES - bitValue;
        }
        data[wordPos] = word;
        return wordPos;
    }

    /***
     * gets the "used" value of a number
     * @param data: the underlying bits array
     * @param number: the number to get the "used" value
     * @return the "used" value of a number
     */
    public boolean getUsedForNumber(int[] data, long number) {
        int wordPos = getWordPosForNumber(number);
        int bitPos = getBitPosInWordForNumber(number);
        return (data[wordPos] & 1 << bitPos) != 0;
    }

    /***
     * get the number that is "used"
     * @param word: the int value containing the bits value for the number
     * @param pos: the position of int value
     * @param used: true for used, false for unused
     * @return the number that is used
     */
    public long getNumberUsed(int word, int pos, boolean used) {
        int bitPos;
        bitPos = getFirstBitPosUsedInWord(word, used);
        if (bitPos < 0) {
            return -1;
        }
        return ((long) pos) * WORD_SIZE + bitPos;
    }
}
