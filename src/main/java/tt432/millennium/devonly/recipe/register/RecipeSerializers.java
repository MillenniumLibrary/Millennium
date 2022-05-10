package tt432.millennium.devonly.recipe.register;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tt432.millennium.devonly.recipe.recipe.CocktailRecipe;
import tt432.millennium.recipes.base.BaseSerializer;

/**
 * @author DustW
 **/
public class RecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RecipeManager.MOD_ID);

    public static final RegistryObject<BaseSerializer<?>> COCKTAIL =
            SERIALIZER.register("cocktail", () -> new BaseSerializer<>(CocktailRecipe.class));

    public static void register(IEventBus bus) {
        SERIALIZER.register(bus);
    }
}
