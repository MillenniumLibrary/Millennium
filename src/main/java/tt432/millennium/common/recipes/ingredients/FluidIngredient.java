package tt432.millennium.common.recipes.ingredients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Predicate;

/**
 * fluid 版本的 ExItemIngredient
 * @see ExItemIngredient
 * @author DustW
 **/
public class FluidIngredient implements Predicate<FluidStack> {
    @Expose
    @SerializedName("fluid")
    public List<SingleFluid> fluidIngredients;

    public FluidIngredient(SingleFluid... fluids) {
        this.fluidIngredients = List.of(fluids);
    }

    public void shrink(FluidStack fluidStack) {
        for (SingleFluid fluidIngredient : fluidIngredients) {
            if (fluidIngredient.test(fluidStack)) {
                fluidStack.shrink(fluidIngredient.amount);
                break;
            }
        }
    }

    @Override
    public boolean test(FluidStack input) {
        return fluidIngredients.stream().anyMatch(fluid -> fluid.test(input));
    }

    public static class SingleFluid implements Predicate<FluidStack> {

        private Fluid fluidCache;
        private TagKey<Fluid> tagCache;
        private boolean tag;
        private boolean cached;

        @Expose
        @SerializedName("fluid")
        public String fluidName;
        @Expose
        public int amount;
        @Expose
        public NbtIngredient nbt;

        public SingleFluid(String fluidName, int amount) {
            this.fluidName = fluidName;
            this.amount = amount;
        }

        public SingleFluid(Fluid fluid, int amount) {
            this(fluid.getRegistryName().toString(), amount);
            tag = false;
            fluidCache = fluid;
        }

        public SingleFluid(FluidStack fluidStack) {
            this(fluidStack.getFluid(), fluidStack.getAmount());
        }

        public SingleFluid(TagKey<Fluid> tag, int amount) {
            this("#" + tag.location(), amount);
            this.tag = true;
            tagCache = tag;
        }

        @Override
        public boolean test(FluidStack stack) {
            if (!cached) {
                if (fluidName.startsWith("#")) {
                    tag = true;
                    tagCache = FluidTags.create(new ResourceLocation(fluidName.substring(1)));
                }
                else {
                    fluidCache = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
                }

                cached = true;
            }

            if (tag) {
                return stack.getFluid().is(tagCache);
            }
            else {
                return stack.getFluid() == fluidCache && stack.getAmount() >= amount;
            }
        }
    }
}
