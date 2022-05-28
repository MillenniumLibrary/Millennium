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
 * @see tt432.millennium.devonly.recipe.register.RecipeTypes
 * @see tt432.millennium.devonly.recipe.recipe.EnchantTestRecipe
 * @author DustW
 **/
public abstract class BaseRecipe<T extends Container> implements Recipe<T> {
    public abstract static class Base extends BaseRecipe<Container> {

    }

    @Expose(deserialize = false, serialize = false)
    protected ResourceLocation id;
    @Expose
    public String type;

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
