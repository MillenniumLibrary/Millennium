package tt432.millennium.devonly.datagen.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import tt432.millennium.recipes.base.BaseRecipe;
import tt432.millennium.utils.json.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author DustW
 **/
public abstract class DataGenRecipes {
    private final Map<ResourceLocation, Map.Entry<String, String>> recipes = new HashMap<>();

    protected abstract void addRecipes();

    protected final void addRecipe(ResourceLocation name, String recipe, String subPath) {
        recipes.put(name, new HashMap.SimpleEntry<>(recipe, subPath));
    }

    public Map<ResourceLocation, Map.Entry<String, String>> getRecipes() {
        addRecipes();
        return recipes;
    }

    protected ResourceLocation defaultName(Item item) {
        return item.getRegistryName();
    }

    protected <C extends Container, TYPE extends BaseRecipe<C>> String baseRecipe(TYPE recipe) {
        return JsonUtil.INSTANCE.pretty.toJson(recipe);
    }
}
