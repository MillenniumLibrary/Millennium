package tt432.millennium.common.recipes.ingredients;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

/**
 * @author DustW
 **/
public class EffectIngredient implements Predicate<MobEffectInstance> {

    @Expose String effect;
    @Expose IntRangeIngredient level;
    @Expose IntRangeIngredient duration;

    MobEffect cache;

    public EffectIngredient(IntRangeIngredient level, IntRangeIngredient duration, MobEffect cache) {
        this.level = level;
        this.duration = duration;
        this.cache = cache;
        this.effect = cache.getRegistryName().toString();
    }

    @Override
    public boolean test(MobEffectInstance input) {
        cache();
        return input.getEffect() == cache && level.test(input.getAmplifier()) && duration.test(input.getDuration());
    }

    protected void cache() {
        if (cache == null) {
            var effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(this.effect));

            if (effect != null) {
                cache = effect;
            }
            else {
                throw new JsonParseException(new TranslatableComponent("error.EffectIngredient.effect.notfound", this.effect).getString());
            }
        }
    }
}
