package tt432.millennium.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import tt432.millennium.util.json.serializer.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author DustW
 **/
public enum JsonUtils {
    /** 最 佳 单 例 */
    INSTANCE;
    public final Gson normal;
    public final Gson pretty;

    JsonUtils() {
        GsonBuilder builder = builder();

        // 无视 @Expose 注解的 Gson 实例
        normal = builder.create();

        builder.setPrettyPrinting();
        pretty = builder.create();
    }

    GsonBuilder builder() {
        return new GsonBuilder()
                // 关闭 html 转义
                .disableHtmlEscaping()
                // 开启复杂 Map 的序列化
                .enableComplexMapKeySerialization()
                // 注册自定义类型的序列化/反序列化器
                .registerTypeAdapter(Ingredient.class, new IngredientSerializer())
                .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
                .registerTypeAdapter(NonNullList.class, new NonNullListSerializer())
                .registerTypeAdapter(ItemStackHandler.class, new ItemStackHandlerSerializer())
                .registerTypeAdapter(ResourceLocation.class, new ResourceLocationSerializer())
                .registerTypeAdapter(FluidStack.class, new FluidStackSerializer());
        //   附带子类
        //   registerTypeHierarchyAdapter
    }

    public static byte[] compress(String string) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try(GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(string.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public static String uncompress(byte[] string) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(string);

        try(GZIPInputStream gzip = new GZIPInputStream(in)) {
            byte[] buffer = new byte[256];
            int index;

            while ((index = gzip.read(buffer)) >= 0) {
                out.write(buffer, 0, index);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();
    }
}
