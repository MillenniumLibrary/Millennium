package tt432.millennium.utils.json.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class ItemStackHandlerSerializer implements BaseSerializer<ItemStackHandler> {

    @Override
    public ItemStackHandler deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ItemStackHandler(context.deserialize(json, NonNullList.class));
    }

    @Override
    public JsonElement serialize(ItemStackHandler src, Type typeOfSrc, JsonSerializationContext context) {
        List<ItemStack> itemList = new ArrayList<>();

        for (int i = 0; i < src.getSlots(); i++) {
            itemList.add(src.getStackInSlot(i));
        }

        return context.serialize(itemList);
    }
}
