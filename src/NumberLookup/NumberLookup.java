package NumberLookup;

import java.util.function.Predicate;

public interface NumberLookup {

    /***
     * Updates the lookup layers when a number value is changed
     * @param number: the number whose value was changed
     * @param lookupUpdatePredicate: a predicate to get the next layer value
     */
    public void updateLookups(long number, Predicate<boolean[]> lookupUpdatePredicate);

    /***
     * Gets the first number that has the value "used"
     * @param used: the value type of number to get.  True for used number, False for unused number
     * @return char[] of first number found, or null if no number has value "used"
     */
    public char[] getFirstNumberUsed(boolean used);
}
