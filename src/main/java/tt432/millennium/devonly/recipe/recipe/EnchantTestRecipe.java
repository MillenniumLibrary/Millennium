package tt432.millennium.devonly.recipe.recipe;

import com.google.gson.annotations.Expose;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.util.RecipeMatcher;
import tt432.millennium.devonly.DevRegistry;
import tt432.millennium.recipes.base.BaseRecipe;
import tt432.millennium.recipes.ingredients.ExItemIngredient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class EnchantTestRecipe extends BaseRecipe<CraftingContainer> implements CraftingRecipe {
    @Expose
    List<ExItemIngredient> ingredients;
    @Expose
    ItemStack result;

    public EnchantTestRecipe(List<ExItemIngredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;

        type = "millennium:enchant_test_recipe";
    }

    @Override
    public boolean matches(List<ItemStack> inputs) {
        inputs = new ArrayList<>(inputs);
        inputs.removeIf(ItemStack::isEmpty);
        return RecipeMatcher.findMatches(inputs, ingredients) != null;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, ingredients.toArray(new ExItemIngredient[0]));
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DevRegistry.ENCHANT_TEST_RECIPE.get();
    }
}
