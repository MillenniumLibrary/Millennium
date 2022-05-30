package tt432.millennium.recipes.stack;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class EffectStack implements Supplier<MobEffectInstance> {

    @Expose
    public String effect;
    @Expose
    public int level;
    @Expose
    public int duration;

    private Supplier<MobEffectInstance> cache;

    @Override
    public MobEffectInstance get() {
        if (cache == null) {
            var effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(this.effect));

            if (effect != null) {
                cache = () -> new MobEffectInstance(effect, duration, level - 1);
            }
            else {
                throw new JsonParseException(new TranslatableComponent("error.EffectStack.effect.notfound", this.effect).getString());
            }
        }

        return cache.get();
    }
}
