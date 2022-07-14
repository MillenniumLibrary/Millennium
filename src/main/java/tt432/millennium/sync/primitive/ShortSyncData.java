package tt432.millennium.sync.primitive;

import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.sync.SyncData;

/**
 * @author DustW
 **/
public class ShortSyncData extends SyncData<Short> {
    public ShortSyncData(String name, Short defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected Tag toTag() {
        return ShortTag.valueOf(get());
    }

    @Override
    protected Short fromTag(Tag tag) {
        return ((ShortTag) tag).getAsShort();
    }
}
