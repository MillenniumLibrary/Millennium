package tt432.millennium.common.sync.primitive;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.common.sync.SyncData;

/**
 * @author DustW
 **/
public class BoolSyncData extends SyncData<Boolean> {
    public BoolSyncData(String name, Boolean defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected ByteTag toTag() {
        return ByteTag.valueOf(get());
    }

    @Override
    protected Boolean fromTag(Tag tag) {
        return ((ByteTag) tag).getAsByte() != 0;
    }
}
