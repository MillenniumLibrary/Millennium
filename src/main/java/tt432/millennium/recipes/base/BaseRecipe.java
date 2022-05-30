package tt432.millennium.recipes.base;

import com.google.gson.annotations.Expose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * 如果你不是很想读，请注意：只有有 Expose 注解的字段才可能会被序列化反序列化。
 *
 * @see tt432.millennium.devonly.recipe.recipe.EnchantTestRecipe
 * @author DustW
 **/
public abstract class BaseRecipe<T extends Container> implements Recipe<T> {
    /** 如无特殊需求，请继承此类 */
    public abstract static class Base extends BaseRecipe<Container> {

    }

    @Expose(deserialize = false, serialize = false)
    protected ResourceLocation id;
    @Expose
    public String type;

    /**
     * 泛化一下尝试与其他类型的容器进行兼容。
     * 如果需要流体之类的其他输入还请自行添加。
     *
     * @param inputs 输入的物品
     * @return       是否匹配合成表
     */
    public abstract boolean matches(List<ItemStack> inputs);

    @Override
    public boolean matches(T pContainer, Level pLevel) {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            list.add(pContainer.getItem(i));
        }
        return matches(list);
    }

    @Override
    public ItemStack assemble(T pContainer) {
        return getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public void setID(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BaseRecipe recipe && recipe.id.equals(id);
    }
}
