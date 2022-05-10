package tt432.millennium.devonly.recipe.register;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tt432.millennium.devonly.recipe.recipe.CocktailRecipe;
import tt432.millennium.recipes.base.BaseRecipe;

/**
 * @author DustW
 **/
public class RecipeTypes {
    private static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registry.RECIPE_TYPE.key(), RecipeManager.MOD_ID);

    public static final RegistryObject<RecipeType<CocktailRecipe>> COCKTAIL = register("cocktail");

    private static <TYPE extends BaseRecipe> RegistryObject<RecipeType<TYPE>> register(String name) {
        return TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return new ResourceLocation(RecipeManager.MOD_ID, name).toString();
            }
        });
    }

    public static void register(IEventBus bus) {
        TYPES.register(bus);
    }
}
