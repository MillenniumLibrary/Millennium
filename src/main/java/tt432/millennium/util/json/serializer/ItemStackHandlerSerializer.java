package tt432.millennium.util.json.serializer;

import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.lang.reflect.Type;

/**
 * @author DustW
 **/
public class ItemStackHandlerSerializer implements BaseSerializer<ItemStackHandler> {

    @Override
    public ItemStackHandler deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray array = json.getAsJsonArray();
        ItemStack[] list = new ItemStack[array.size()];

        for (int i = 0; i < array.size(); i++) {
            list[i] = context.deserialize(array.get(i).getAsJsonObject(), ItemStack.class);
        }

        return new ItemStackHandler(NonNullList.of(ItemStack.EMPTY, list));
    }

    @Override
    public JsonElement serialize(ItemStackHandler src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray itemList = new JsonArray();

        for (int i = 0; i < src.getSlots(); i++) {
            itemList.add(context.serialize(src.getStackInSlot(i)));
        }

        return itemList;
    }
}
