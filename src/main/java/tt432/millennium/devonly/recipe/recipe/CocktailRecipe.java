package tt432.millennium.devonly.recipe.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import tt432.millennium.devonly.recipe.register.RecipeSerializers;
import tt432.millennium.devonly.recipe.register.RecipeTypes;
import tt432.millennium.recipes.base.BaseRecipe;
import tt432.millennium.recipes.stack.EffectStack;

import java.util.List;

/**
 * @author DustW
 **/
public class CocktailRecipe extends BaseRecipe {
    @Override
    public boolean matches(List<ItemStack> inputs) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.COCKTAIL.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.COCKTAIL.get();
    }

    @Expose
    public String author;
    @Expose
    public Content content;

    public static class Content {
        @Expose
        List<Ingredient> recipe;
        @Expose
        @SerializedName("craftingtime")
        int craftingTime;
        @Expose
        public int hunger;
        @Expose
        public int saturation;
        @Expose
        public List<EffectStack> effect;
    }
}
