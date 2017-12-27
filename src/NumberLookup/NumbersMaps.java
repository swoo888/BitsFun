package NumberLookup;

public class NumbersMaps implements NumbersMap {
    private static final long MAX_NUMBER = 9999999999L;
    private WordHelper helper;
    private NumberLookup anyUsedNumberLookup, allUsedNumberLookup;
    private int[] data;

    public NumbersMaps(WordHelper helper, NumberLookup lookup) {
        data = new int[helper.getNumWords(MAX_NUMBER)];
        anyUsedNumberLookup = lookup.build(data, helper);
        allUsedNumberLookup = lookup.build(data, helper);
        this.helper = helper;
    }

    @Override
    public char[] get(boolean used) {
        int wordPos, bitPos;
        if (used) {
            wordPos = anyUsedNumberLookup.getWordPos(true);
            if (wordPos < 0)
                return null;
            bitPos = helper.getFirstBitPosSet(data[wordPos]);
        } else {
            wordPos = allUsedNumberLookup.getWordPos(false);
            if (wordPos < 0)
                return null;
            bitPos = helper.getFirstBitPosUnset(data[wordPos]);
        }
        long n = helper.getNumber(wordPos, bitPos);
        return String.format("%010d", n).toCharArray();
    }

    @Override
    public boolean get(char[] number) {
        long n = getNumber(number);
        int wordPos = helper.getWordPos(n);
        int bitPos = helper.getBitPos(n);
        int w = data[wordPos];
        return helper.getBitValue(w, bitPos);
    }

    @Override
    public void set(char[] number, boolean used) {
        long n = getNumber(number);
        int wordPos = helper.getWordPos(n);
        int bitPos = helper.getBitPos(n);
        int updated = helper.getUpdatedWordValue(data[wordPos], bitPos, used);
        data[wordPos] = updated;
        anyUsedNumberLookup.update(wordPos, w -> w != 0);
        allUsedNumberLookup.update(wordPos, w -> w == helper.getWordOnes());
    }

    private long getNumber(char[] number) {
        long n;
        try {
            n = Long.parseLong(String.valueOf(number));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid number: %s", String.valueOf(number)));
        }
        checkNumber(n);
        return n;
    }

    private void checkNumber(long number) {
        if (number < 0 || number > MAX_NUMBER) {
            throw new IllegalArgumentException(String.format("Only supports number from 0 to %d", MAX_NUMBER));
        }
    }
}
