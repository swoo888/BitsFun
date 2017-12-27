package NumberLookup;

public class Helper {
    private static final int WORD_ONES = 0xFFFFFFFF;
    private static final int WORD_SIZE = 32;

    private boolean getBitValue(int word, int bitPos) {
        return (word & 1 << bitPos) != 0;
    }

    private int getUpdatedWordValue(int word, int bitIdx, boolean used) {
        int bitValue = 1 << bitIdx;

        if (used) {
            word |= bitValue;
        } else {
            word &= WORD_ONES - bitValue;
        }
        return word;
    }

    private int getWordPos(long n) {
        return (int) (n / WORD_SIZE);
    }

    private int getBitPos(long n) {
        return (int) (n % WORD_SIZE);
    }

    public int getWordOnes() {
        return WORD_ONES;
    }

    public long getNumber(int wordPos, int bitPos) {
        return ((long) wordPos) * WORD_SIZE + bitPos;
    }

    public int getArraySize(long n) {
        return (int) (n / WORD_SIZE) + (n % WORD_SIZE == 0 ? 0 : 1);
    }

    public int getFirstBitPosSet(int word) {
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
    }

    public int getFirstBitPosUnset(int word) {
        if (word == 0)
            return 0;
        if (word == WORD_ONES)
            return -1;
        word = ~word;
        return getFirstBitPosSet(word);
    }

    public int getUpperPos(int pos, int[] data, boolean used) {
        int word = data[pos];
        int bitPos;
        if (used) {
            bitPos = getFirstBitPosSet(word);
        } else {
            bitPos = getFirstBitPosUnset(word);
        }
        if (bitPos < 0) {
            return -1;
        }
        return pos * WORD_SIZE + bitPos;
    }

    public int updateValue(int[] data, long n, boolean used) {
        int wordPos = getWordPos(n);
        int bitPos = getBitPos(n);
        int updated = getUpdatedWordValue(data[wordPos], bitPos, used);
        data[wordPos] = updated;
        return wordPos;
    }

    public boolean getValue(int[] data, long n) {
        int wordPos = getWordPos(n);
        int bitPos = getBitPos(n);
        int w = data[wordPos];
        return getBitValue(w, bitPos);
    }
}
