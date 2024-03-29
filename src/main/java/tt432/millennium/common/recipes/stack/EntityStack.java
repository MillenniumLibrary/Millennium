package tt432.millennium.common.recipes.stack;

import com.google.gson.annotations.Expose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author DustW
 **/
public class EntityStack<T extends Entity> {

    EntityType<T> type;

    @Expose String entity;
    @Expose @Nullable String nbt;
    @Expose int count;

    @ParametersAreNonnullByDefault
    public EntityStack(EntityType<T> type) {
        this(type, 1);
    }

    @ParametersAreNonnullByDefault
    public EntityStack(EntityType<T> type, int count) {
        this.type = type;
        this.entity = type.getRegistryName().toString();
        this.count = count;
    }

    @ParametersAreNonnullByDefault
    public EntityStack(EntityType<T> type, @Nullable String nbt, int count) {
        this(type, count);
        this.nbt = nbt;
    }

    public EntityType<T> get() {
        return type == null ? type = (EntityType<T>) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity)) : type;
    }

    public int getCount() {
        return count;
    }
}
