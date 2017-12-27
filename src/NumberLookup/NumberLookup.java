package NumberLookup;

import java.util.function.IntPredicate;

/**
 * This class manages the lookup chain of numbers.  It is building layers of int arrays.  With the first
 * layer being the top most array for the underlying data.  Each layer below is reduced by int size of 32.
 * Such that first bit of layer N+1 is referring to first word (32bits) of layer N.
 * The last layer is of one int.
 */
public class NumberLookup {
    private NumberLookup topNode, botNode, tail;
    private Helper helper;
    private int[] data;

    /**
     * builds multiple layers of lookup arrays for the underlying data.
     *
     * @param data:   array of underlying data to build a lookup layer.
     * @param helper: bit calculation util helper
     * @return head of the layer chain.
     */
    public NumberLookup build(int[] data, Helper helper) {
        NumberLookup head = new NumberLookup();
        head.data = data;
        head.helper = helper;
        NumberLookup cur = head;

        int size = helper.getArraySize(cur.data.length);
        while (size >= 1) {
            NumberLookup lu = new NumberLookup();
            lu.data = new int[size];
            lu.topNode = cur;
            cur.botNode = lu;
            cur = lu;
            if (size <= 1) {
                break;
            }
            size = helper.getArraySize(size);
        }
        head.tail = cur;
        return head;
    }

    /**
     * Updates lookup layers when a position is changed in underlying data.
     *
     * @param pos:             the position that is changed
     * @param updatePredicate: a predicate returns the bit value to set for a changed word
     */
    public void update(int pos, IntPredicate updatePredicate) {
        NumberLookup cur = this;
        while (cur != null) {
            if (cur.botNode == null)
                return;
            int value = cur.data[pos];
            boolean used = updatePredicate.test(value);
            pos = helper.updateValue(cur.botNode.data, pos, used);
            cur = cur.botNode;
        }
    }

    /**
     * Returns the first word position of the underlying data array that has value "used"
     *
     * @param used: true for first value that is set.  false for first value that is unset
     * @return the first word position of the underlying data array that contains the bit value "used"
     */
    public char[] getFirstNumber(boolean used) {
        NumberLookup cur = tail;
        int pos = 0;
        while (cur != null) {
            if (cur.topNode == null)
                break;
            int upperPos = helper.getUpperPos(pos, cur.data, used);
            if (upperPos < 0) {
                pos = -1;
                break;
            }
            pos = upperPos;
            cur = cur.topNode;
        }
        if (pos < 0) {
            return null;
        }
        int bitPos;
        if (used) {
            bitPos = helper.getFirstBitPosSet(data[pos]);
        } else {
            bitPos = helper.getFirstBitPosUnset(data[pos]);
        }
        if (bitPos < 0) {
            return null;
        }
        long n = helper.getNumber(pos, bitPos);
        return String.format("%010d", n).toCharArray();
    }
}
