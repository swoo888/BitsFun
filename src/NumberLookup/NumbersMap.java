package NumberLookup;

public interface NumbersMap {
    /**
     *  Constant time look up of the first number that matches boolean condition "used".
      * @param used: type of number to get.  True for first number that is used.  False for first number that is not used.
     * @return char array of the first number that matches input param used.
     */
    char[] get(boolean used);

    /**
     * Constant time look up of the current status of a number.
     * @param number: the number to search
     * @return true if number is used, else false
     */
    boolean get(char[] number);

    /**
     * Constant time to set a number's "used" status
     * @param number: the number to set its status
     * @param used: the new status for the number.  True to indicate a number is used, False for unused.
     */
    void set(char[] number, boolean used);
}
