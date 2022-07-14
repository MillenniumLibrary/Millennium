package tt432.millennium.sync.object;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.sync.SyncData;

/**
 * @author DustW
 **/
public class StringSyncData extends SyncData<String> {

    public StringSyncData(String name, String defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    public boolean isEmpty() {
        return get().isEmpty();
    }

    @Override
    protected StringTag toTag() {
        return StringTag.valueOf(get());
    }

    @Override
    protected String fromTag(Tag tag) {
        return tag.getAsString();
    }
}
