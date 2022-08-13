package tt432.millennium.common.sync.primitive;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.common.sync.SyncData;

/**
 * @author DustW
 **/
public class IntSyncData extends SyncData<Integer> {
    public IntSyncData(String name, Integer defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected IntTag toTag() {
        return IntTag.valueOf(get());
    }

    @Override
    protected Integer fromTag(Tag tag) {
        return ((IntTag) tag).getAsInt();
    }

    public int reduce(int value) {
        this.set(get() - value);
        return get();
    }

    public int reduce(int value, int min) {
        this.set(Math.max(get() - value, min));
        return get();
    }

    public int plus(int value) {
        this.set(get() + value);
        return get();
    }

    public int plus(int value, int max) {
        this.set(Math.min(get() + value, max));
        return get();
    }
}
