package tt432.millennium.data.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import tt432.millennium.common.recipes.base.BaseRecipe;
import tt432.millennium.util.json.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DustW
 **/
public abstract class AbstractRecipeSet {
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

    protected <T extends BaseRecipe<?>> String baseRecipe(T recipe) {
        return JsonUtils.INSTANCE.pretty.toJson(recipe);
    }
}
