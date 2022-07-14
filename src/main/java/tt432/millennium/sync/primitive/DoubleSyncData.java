package tt432.millennium.sync.primitive;

import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.Tag;
import tt432.millennium.sync.SyncData;

/**
 * @author DustW
 **/
public class DoubleSyncData extends SyncData<Double> {
    public DoubleSyncData(String name, Double defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected Tag toTag() {
        return DoubleTag.valueOf(get());
    }

    @Override
    protected Double fromTag(Tag tag) {
        return ((DoubleTag) tag).getAsDouble();
    }
}
