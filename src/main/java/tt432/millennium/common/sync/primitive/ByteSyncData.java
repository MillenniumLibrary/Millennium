package tt432.millennium.common.sync.primitive;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.common.sync.SyncData;

/**
 * @author DustW
 **/
public class ByteSyncData extends SyncData<Byte> {
    public ByteSyncData(String name, Byte defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected Tag toTag() {
        return ByteTag.valueOf(get());
    }

    @Override
    protected Byte fromTag(Tag tag) {
        return ((ByteTag) tag).getAsByte();
    }
}
