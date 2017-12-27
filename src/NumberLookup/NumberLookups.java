package NumberLookup;

import java.util.function.IntPredicate;

public class NumberLookups implements NumberLookup {
    private NumberLookups topNode, botNode, tail;
    private WordHelper wordHelper;
    private int[] data;

    @Override
    public NumberLookup build(int[] data, WordHelper helper) {
        NumberLookups head = new NumberLookups();
        head.data = data;
        head.wordHelper = helper;
        NumberLookups cur = head;

        int numWords = helper.getNumWords(cur.data.length);
        while (numWords >= 1) {
            NumberLookups lu = new NumberLookups();
            lu.data = new int[numWords];
            lu.topNode = cur;
            cur.botNode = lu;
            cur = lu;
            if (numWords <= 1) {
                break;
            }
            numWords = helper.getNumWords(numWords);
        }
        head.tail = cur;
        return head;
    }

    @Override
    public void update(int wordPos, IntPredicate updatePredicate) {
        NumberLookups cur = this;
        while (cur != null) {
            if (cur.botNode == null)
                return;
            int word = cur.data[wordPos];
            boolean used = updatePredicate.test(word);
            int botWordPos = wordHelper.getWordPos(wordPos);
            int botWordBitPos = wordHelper.getBitPos(wordPos);
            int orig = cur.botNode.data[botWordPos];
            int updated = wordHelper.getUpdatedWordValue(orig, botWordBitPos, used);
            cur.botNode.data[botWordPos] = updated;
            cur = cur.botNode;
            wordPos = botWordPos;
        }
    }

    @Override
    public int getWordPos(boolean used) {
        NumberLookups cur = tail;
        int wordPos = 0;
        while (cur != null) {
            int word = cur.data[wordPos];
            int bitPos;
            if (used) {
                bitPos = wordHelper.getFirstBitPosSet(word);
            } else {
                bitPos = wordHelper.getFirstBitPosUnset(word);
            }
            if (bitPos < 0) {
                return -1;
            }
            if (cur.topNode == null)
                break;
            wordPos = wordHelper.getUpperWordIdx(wordPos, bitPos);
            cur = cur.topNode;
        }
        return wordPos;
    }
}
