package tt432.millennium.dependencies.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import tt432.millennium.Millennium;

import java.util.List;

/**
 * @author DustW
 **/
@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    // public static final RecipeType<CocktailRecipe> COCKTAIL =
    //         new RecipeType<>(new ResourceLocation(Millennium.MOD_ID, "cocktail"),
    //                 CocktailRecipe.class);

    protected <C extends Container, T extends Recipe<C>> List<T> getRecipe(net.minecraft.world.item.crafting.RecipeType<T> recipeType) {
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(recipeType);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        //registry.addRecipeCategories(new CocktailRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        //registration.addRecipes(COCKTAIL, getRecipe(RecipeTypes.COCKTAIL.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // registration.addRecipeCatalyst(new ItemStack(BlockRegistry.COOKING_POT.get()), COOKING_POT_JEI_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // registration.addRecipeClickArea(CookingPotScreen.class, 94, 16, 32, 54, COOKING_POT_JEI_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        // registration.addRecipeTransferHandler(CookingPotContainer.class, COOKING_POT_JEI_TYPE, 0, 9, 10, 36);
    }

    public static final ResourceLocation UID = new ResourceLocation(Millennium.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }
}
