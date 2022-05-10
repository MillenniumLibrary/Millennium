package tt432.millennium.devonly.recipe.register;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import tt432.millennium.Millennium;
import tt432.millennium.devonly.recipe.recipe.CocktailRecipe;
import tt432.millennium.recipes.base.BaseRecipe;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DustW
 **/
public class RecipeManager {
    public static final String MOD_ID = Millennium.MOD_ID;

    public static <T extends BaseRecipe> List<T> getRecipe(Level level, RecipeType<T> type, List<ItemStack> itemStacks) {
        return level.getRecipeManager().getAllRecipesFor(type).stream().filter(s -> s.matches(itemStacks)).collect(Collectors.toList());
    }

    public static List<CocktailRecipe> getCocktailRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(RecipeTypes.COCKTAIL.get());
    }

    public static void register(IEventBus bus) {
        RecipeTypes.register(bus);
        RecipeSerializers.register(bus);
    }
}
