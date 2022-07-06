package tt432.millennium.recipes.ingredients;

import com.google.gson.annotations.Expose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author DustW
 **/
public class EntityIngredient<T extends Entity> implements Predicate<T> {

    @Expose List<SingleEntity<T>> entities;

    public EntityIngredient(List<SingleEntity<T>> entities) {
        this.entities = entities;
    }

    @Override
    public boolean test(T t) {
        return entities.stream().anyMatch(entity -> entity.test(t));
    }

    public static class SingleEntity<T extends Entity> implements Predicate<T> {

        private EntityType<T> entityCache;
        private boolean cached;

        @Expose String entity;

        public SingleEntity(EntityType<T> entity) {
            this.entityCache = entity;
            this.entity = entity.getRegistryName().toString();
        }

        @Override
        public boolean test(T entity) {
            if (!cached) {
                entityCache = (EntityType<T>) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(this.entity));
                cached = true;
            }

            return entity.getType() == entityCache;
        }
    }
}
