package tt432.millennium.common.sync;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author DustW
 **/
public abstract class SyncData<V> implements NonNullSupplier<V> {
    private V value;
    private boolean changed;
    private final String name;
    public final boolean needSave;

    protected SyncData(String name, V defaultValue, boolean needSave) {
        this.value = defaultValue;
        this.name = name;
        this.needSave = needSave;
    }

    protected abstract Tag toTag();
    protected abstract V fromTag(Tag tag);

    @Override
    public @NotNull V get() {
        return value;
    }

    public void set(V value) {
        if (!this.value.equals(value)) {
            this.value = value;
            onChanged();
        }
    }

    public void save(CompoundTag tag, boolean force) {
        if (changed || force) {
            tag.put(name, toTag());
            changed = false;
        }
    }

    public void load(CompoundTag tag) {
        if (tag.contains(name)) {
            value = fromTag(tag.get(name));
            onChanged();
        }
    }

    protected void onChanged() {
        this.changed = true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof SyncData sd && Objects.equals(sd.name, this.name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "SyncData: {name: %s, value: %s}".formatted(name, value.toString());
    }
}
