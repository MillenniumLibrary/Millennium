package tt432.millennium.recipes.base;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tt432.millennium.utils.json.JsonUtil;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

/**
 * 大概是不需要动的
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
    @ParametersAreNonnullByDefault
    public RECIPE fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        var result = JsonUtil.INSTANCE.normal.fromJson(buffer.readUtf(), recipeClass);
        result.setID(recipeId);
        return result;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void toNetwork(FriendlyByteBuf pBuffer, RECIPE recipe) {
        pBuffer.writeUtf(JsonUtil.INSTANCE.normal.toJson(recipe));
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public RECIPE fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        var result = JsonUtil.INSTANCE.normal.fromJson(serializedRecipe, recipeClass);
        result.setID(recipeId);
        return result;
    }

    public JsonObject toJson(RECIPE pRecipe) {
        return JsonUtil.INSTANCE.normal.toJsonTree(pRecipe).getAsJsonObject();
    }

    public static <C extends Container, R extends BaseRecipe<C>> Supplier<BaseSerializer<C, R>> of(Class<R> recipeClass) {
        return () -> new BaseSerializer<>(recipeClass);
    }
}
