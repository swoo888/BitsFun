package BitArray;

public interface BitArray {
    /**
     * gets the size of array
     * @return size
     */
    long getSize();

    /***
     * Gets the number of items for a number if each item is itemSize
     * @param number: the number to calculate the size
     * @param itemSize: each item size
     * @return number of items that is large enough for number if each item is of itemSize.
     */
    int getNumberOfItems(long number, int itemSize);

    /***
     * Creates a new BitArray to hold number of items
     * @param number: number of items
     * @return BitArray object
     */
    BitArray newBitArray(long number);


    boolean[] getValuesInRange(long position, int count);

    /***
     * sets the value at position
     * @param position: the position or number index to set value
     * @param value: the value to set
     */
    void setBitValue(long position, boolean value);

    /***
     * gets the value at position or number
     * @param position: the number or position to get value
     * @return the value at position or number
     */
    boolean getBitValue(long position);

    /***
     * Gets the first number position with value
     * @param value: True for used, False for unused
     * @param start: the start position
     * @param len: number of position to search
     * @return the first position found with value, or -1 if not found
     */
    long getFirstPositionWithValue(boolean value, long start, int len);
}
