package tt432.millennium.sync.object;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import tt432.millennium.sync.SyncData;

/**
 * @author DustW
 **/
public class FluidSyncData extends SyncData<FluidStack> {
    public FluidSyncData(String name, FluidStack defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected CompoundTag toTag() {
        var result = new CompoundTag();
        result.putString("name", get().getFluid().getRegistryName().toString());
        result.putInt("amount", get().getAmount());
        if (get().getTag() != null) {
            result.put("tag", get().getTag());
        }
        return result;
    }

    @Override
    protected FluidStack fromTag(Tag taga) {
        var tag = (CompoundTag) taga;
        var name = tag.getString("name");
        var amount = tag.getInt("amount");
        var tag2 = tag.getCompound("tag");
        return new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(name)), amount, tag2);
    }
}
