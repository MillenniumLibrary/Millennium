package tt432.millennium.common.recipes.base;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tt432.millennium.util.json.JsonUtils;

/**
 * @author DustW
 **/
public class BaseSerializer<C extends Container, RECIPE extends BaseRecipe<C>>
        extends ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<RECIPE> {

    Class<RECIPE> recipeClass;

    public BaseSerializer(Class<RECIPE> recipeClass) {
        this.recipeClass = recipeClass;
    }

    @Override
    public @NotNull RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject serializedRecipe) {
        return JsonUtils.INSTANCE.normal.fromJson(serializedRecipe, recipeClass)
                .setId(recipeId).setSerializer(this);
    }

    @Nullable
    @Override
    public RECIPE fromNetwork(@NotNull ResourceLocation recipeId, FriendlyByteBuf pBuffer) {
        return JsonUtils.INSTANCE.normal.fromJson(JsonUtils.uncompress(pBuffer.readByteArray()), recipeClass)
                .setId(recipeId).setSerializer(this);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, @NotNull RECIPE recipe) {
        pBuffer.writeBytes(JsonUtils.compress(JsonUtils.INSTANCE.normal.toJson(recipe)));
    }
}
