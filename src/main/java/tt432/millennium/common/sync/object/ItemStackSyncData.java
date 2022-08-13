package tt432.millennium.common.sync.object;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import tt432.millennium.common.sync.SyncData;

/**
 * @author DustW
 **/
public class ItemStackSyncData extends SyncData<ItemStack> {
    public ItemStackSyncData(String name, ItemStack defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected CompoundTag toTag() {
        return get().serializeNBT();
    }

    @Override
    protected ItemStack fromTag(Tag tag) {
        return ItemStack.of((CompoundTag) tag);
    }
}
