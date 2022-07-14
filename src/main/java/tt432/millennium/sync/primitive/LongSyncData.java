package tt432.millennium.sync.primitive;

import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.sync.SyncData;

/**
 * @author DustW
 **/
public class LongSyncData extends SyncData<Long> {
    public LongSyncData(String name, Long defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected Tag toTag() {
        return LongTag.valueOf(get());
    }

    @Override
    protected Long fromTag(Tag tag) {
        return ((LongTag) tag).getAsLong();
    }
}
