package tt432.millennium.sync.object;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.items.ItemStackHandler;
import tt432.millennium.sync.SyncData;

/**
 * @author DustW
 **/
public class ItemStackHandlerSyncData extends SyncData<ItemStackHandlerSyncData.SyncDataItemStackHandler> {
    public ItemStackHandlerSyncData(String name, int size, boolean needSave) {
        super(name, new SyncDataItemStackHandler(size), needSave);
        get().setData(this);
    }

    @Override
    public void set(SyncDataItemStackHandler value) {
        super.set(value);
    }

    @Override
    protected CompoundTag toTag() {
        return get().serializeNBT();
    }

    @Override
    protected SyncDataItemStackHandler fromTag(Tag tag) {
        get().deserializeNBT((CompoundTag) tag);
        return get();
    }

    public static class SyncDataItemStackHandler extends ItemStackHandler {

        ItemStackHandlerSyncData data;

        public SyncDataItemStackHandler(int size) {
            super(size);
        }

        public void setData(ItemStackHandlerSyncData data) {
            this.data = data;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            data.onChanged();
        }
    }
}
