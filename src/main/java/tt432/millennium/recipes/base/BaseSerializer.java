package tt432.millennium.recipes.base;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;
import tt432.millennium.utils.json.JsonUtil;

/**
 * @see tt432.millennium.devonly.recipe.register.RecipeSerializers
 * @author DustW
 **/
public class BaseSerializer<C extends Container, RECIPE extends BaseRecipe<C>>
        extends ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<RECIPE> {

    Class<RECIPE> recipeClass;

    public BaseSerializer(Class<RECIPE> recipeClass) {
        this.recipeClass = recipeClass;
    }

    @Nullable
    @Override
    public RECIPE fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        return JsonUtil.INSTANCE.normal.fromJson(pBuffer.readUtf(), recipeClass);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, RECIPE pRecipe) {
        pBuffer.writeUtf(JsonUtil.INSTANCE.normal.toJson(pRecipe));
    }

    @Override
    public RECIPE fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        var result = JsonUtil.INSTANCE.normal.fromJson(pSerializedRecipe, recipeClass);
        result.setID(pRecipeId);
        return result;
    }

    public JsonObject toJson(RECIPE pRecipe) {
        return JsonUtil.INSTANCE.normal.toJsonTree(pRecipe).getAsJsonObject();
    }
}
