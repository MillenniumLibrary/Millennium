package tt432.millennium.dependencies.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tt432.millennium.Millennium;
import tt432.millennium.dependencies.jei.JeiPlugin;
import tt432.millennium.devonly.DevRegistry;
import tt432.millennium.devonly.recipe.recipe.CocktailRecipe;

/**
 * @author DustW
 **/
public class CocktailRecipeCategory extends BaseRecipeCategory<CocktailRecipe> {
    protected static final ResourceLocation BACKGROUND =
            new ResourceLocation(Millennium.MOD_ID, "textures/jei/cocktail");

    public CocktailRecipeCategory(IGuiHelper helper) {
        super(JeiPlugin.COCKTAIL,
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(DevRegistry.COCKTAIL.get())),
                helper.createDrawable(BACKGROUND, 16, 16, 144, 54));
    }
}
