package tt432.millennium.common.sync.primitive;

import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.common.sync.SyncData;

/**
 * @author DustW
 **/
public class FloatSyncData extends SyncData<Float> {
    public FloatSyncData(String name, Float defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected Tag toTag() {
        return FloatTag.valueOf(get());
    }

    @Override
    protected Float fromTag(Tag tag) {
        return ((FloatTag) tag).getAsFloat();
    }
}
