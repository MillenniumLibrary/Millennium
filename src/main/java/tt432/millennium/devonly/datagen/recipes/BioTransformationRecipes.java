package tt432.millennium.devonly.datagen.recipes;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import tt432.millennium.devonly.common.recipe.recipe.BioTransformationRecipe;
import tt432.millennium.recipes.ingredients.EffectIngredient;
import tt432.millennium.recipes.ingredients.EntityIngredient;
import tt432.millennium.recipes.ingredients.IntRangeIngredient;
import tt432.millennium.recipes.stack.EntityStack;

import java.util.List;

/**
 * @author DustW
 **/
public class BioTransformationRecipes extends DataGenRecipes {
    @Override
    protected void addRecipes() {
        addBioRecipe(new BioTransformationRecipe<>(
                new EntityIngredient<>(
                        List.of(new EntityIngredient.SingleEntity<>(
                                EntityType.WOLF
                        ))
                ),
                List.of(
                        new EffectIngredient(
                                new IntRangeIngredient(1, 3),
                                new IntRangeIngredient(1, Integer.MAX_VALUE),
                                MobEffects.DAMAGE_BOOST
                        ),
                        new EffectIngredient(
                                new IntRangeIngredient(0, 3),
                                new IntRangeIngredient(1, Integer.MAX_VALUE),
                                MobEffects.POISON
                        )
                ),
                new EntityStack<>(EntityType.SHEEP)
        ));
    }

    void addBioRecipe(BioTransformationRecipe<?, ?> recipe) {
        addRecipe(recipe.getOutput().get().getRegistryName(), baseRecipe(recipe), "bio_transformation");
    }
}
