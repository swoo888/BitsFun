package NumberLookup;

import java.util.function.IntPredicate;

public interface NumberLookup {
    NumberLookup build(int[] data, WordHelper helper);

    void update(int wordPos, IntPredicate updatePredicate);

    int getWordPos(boolean used);
}
