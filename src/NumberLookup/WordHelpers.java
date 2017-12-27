package NumberLookup;

public class WordHelpers implements WordHelper {
    private static final int WORD_ONES = 0xFFFFFFFF;
    private static final int WORD_SIZE = 32;

    @Override
    public long getNumber(int wordPos, int bitPos) {
        return ((long) wordPos) * WORD_SIZE + bitPos;
    }

    @Override
    public boolean getBitValue(int word, int bitPos) {
        return (word & 1 << bitPos) != 0;
    }

    @Override
    public int getWordOnes() {
        return WORD_ONES;
    }

    @Override
    public int getUpdatedWordValue(int word, int bitIdx, boolean used) {
        int bitValue = 1 << bitIdx;

        if (used) {
            word |= bitValue;
        } else {
            word &= WORD_ONES - bitValue;
        }
        return word;
    }

    @Override
    public int getWordPos(long n) {
        return (int) (n / WORD_SIZE);
    }

    @Override
    public int getBitPos(long n) {
        return (int) (n % WORD_SIZE);
    }

    @Override
    public int getNumWords(long n) {
        return (int) (n / WORD_SIZE) + (n % WORD_SIZE == 0 ? 0 : 1);
    }

    @Override
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

    @Override
    public int getFirstBitPosUnset(int word) {
        if (word == 0)
            return 0;
        if (word == WORD_ONES)
            return -1;
        word = ~word;
        return getFirstBitPosSet(word);
    }

    @Override
    public int getUpperWordIdx(int wordPos, int bitPos) {
        return wordPos * WORD_SIZE + bitPos;
    }
}
