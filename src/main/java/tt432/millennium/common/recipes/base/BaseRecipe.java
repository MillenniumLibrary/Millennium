package tt432.millennium.common.recipes.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import tt432.millennium.common.recipes.impls.TemplateRecipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 * @see TemplateRecipe (使用范例)
 **/
public abstract class BaseRecipe<C extends Container> implements Recipe<C>, Serializable {

    protected transient ResourceLocation id;
    protected transient RecipeSerializer<?> serializer;

    public String type;
    public String group;

    public boolean matches(List<ItemStack> inputs) {
        return RecipeMatcher.findMatches(inputs, getIngredients()) != null;
    }

    @Override
    public boolean matches(C pContainer, Level pLevel) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            items.add(pContainer.getItem(i));
        }

        return matches(items);
    }

    @Override
    public ItemStack assemble(C pContainer) {
        return getResultItem();
    }

    @Override
    public String getGroup() {
        return group == null ? "" : group;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public <T extends BaseRecipe<C>> T setId(ResourceLocation id) {
        this.id = id;
        return (T) this;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public <T extends BaseRecipe<C>> T setSerializer(RecipeSerializer<?> serializer) {
        this.serializer = serializer;
        return (T) this;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return serializer;
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
