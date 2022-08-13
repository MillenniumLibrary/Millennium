package tt432.millennium.util.container;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerUtils {
    public static List<ItemStack> getItems(IItemHandler... handlers) {
        List<ItemStack> result = new ArrayList<>();

        for (IItemHandler handler : handlers) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack inSlot = handler.getStackInSlot(i);
                if (!inSlot.isEmpty())
                    result.add(inSlot);
            }
        }

        return result;
    }

    public static void clearHandlers(ItemStackHandler... handlers) {
        for (var handler : handlers) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack inSlot = handler.getStackInSlot(i);
                if (!inSlot.isEmpty())
                    handler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }
}
