package tt432.millennium.recipes.ingredients;

import com.google.gson.annotations.Expose;

import java.util.function.Predicate;

/**
 * @author DustW
 **/
public class IntRangeIngredient implements Predicate<Integer> {

    @Expose Integer min;
    @Expose Integer max;

    public IntRangeIngredient(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean test(Integer integer) {
        return integer >= min && integer <= max;
    }
}
