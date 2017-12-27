package NumberLookup;

public interface WordHelper {

    int getUpdatedWordValue(int word, int bitIdx, boolean used);

    int getWordPos(long n);

    int getBitPos(long n);

    int getNumWords(long n);

    int getFirstBitPosSet(int word);

    int getFirstBitPosUnset(int word);

    int getUpperWordIdx(int wordPos, int bitPos);

    int getWordOnes();

    boolean getBitValue(int word, int bitPos);

    long getNumber(int wordPos, int bitPos);
}
