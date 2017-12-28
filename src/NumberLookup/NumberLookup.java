package NumberLookup;

import java.util.function.IntPredicate;

/**
 * This class manages the lookup chain for numbers.  It is building layers of int arrays.  With the first
 * layer being the top most array for the underlying data.  Each layer below is reduced by int size of 32.
 * Such that first bit of layer N+1 is referring to first word (32bits) of layer N.
 * The last layer is of one int.
 */
public class NumberLookup {
    /**
     * topNode is the node pointing to higher layer. each NumberLookup object will have a topNode pointing to its higher layer
     * and a botNode pointing to next lower layer.  with the topMost NumberLookup having a topNode of null, and the lowest
     * layer having botNode of null
     */
    private NumberLookup topNode; // the top node, pointing to the NumberLookup object of next higher layer
    private NumberLookup botNode; // the bottum node, pointing to the NumberLookup object of next lower layer
    private NumberLookup tail; // the NumberLookup object where we start searching layer by layer upward
    private BitsManager bitsManager;
    private int[] data;

    public NumberLookup(int[] data, BitsManager bitsManager, NumberLookup topNode){
        this.bitsManager = bitsManager;
        this.data = data;
        this.topNode = topNode;
    }

    public NumberLookup(int[] data, BitsManager bitsManager) {
        this.data = data;
        this.bitsManager = bitsManager;
    }

    /***
     * builds the lookup layers
     * @return the current object after lookup layers is built
     */
    public NumberLookup buildLookups(){
        NumberLookup cur = this;
        int size = bitsManager.getArraySizeForNumber(cur.data.length);
        while (size >= 1) {
            int[] dataLookup = new int[size];
            NumberLookup lu = new NumberLookup(dataLookup, bitsManager, cur);
            cur.botNode = lu;
            cur = lu;
            if (size <= 1) {
                break;
            }
            size = bitsManager.getArraySizeForNumber(size);
        }
        tail = cur;  // saves the tail value for easier searching from bottum up
        return this;
    }

    /**
     * Updates lookup layers when data is changed
     *
     * @param pos:             the position that is changed
     * @param updatePredicate: a predicate to retrieve bit value to set
     */
    public void updateLookups(int pos, IntPredicate updatePredicate) {
        NumberLookup cur = this;
        while (cur != null) {
            if (cur.botNode == null)
                return;
            int value = cur.data[pos];
            boolean used = updatePredicate.test(value);
            pos = bitsManager.setUsedForNumber(cur.botNode.data, pos, used);
            cur = cur.botNode;
        }
    }

    /**
     * look through all layers by indexing to find a number that is "used"
     *
     * @param used: true for used, false for unused
     * @return the number that is used if used is true, else unused
     */
    public char[] getNumberUsed(boolean used) {
        NumberLookup cur = tail;
        int pos = 0;
        while (cur != null) {
            if (cur.topNode == null)
                break;
            int upperPos = bitsManager.getUpperLayerPosUsed(pos, cur.data, used);
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
        long number = bitsManager.getNumberUsed(data[pos], pos, used);
        if (number < 0)
            return null;
        return String.format("%010d", number).toCharArray();
    }
}
