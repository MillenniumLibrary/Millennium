package tt432.millennium.common.recipes.impls;

import com.google.gson.annotations.Expose;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.RecipeMatcher;
import tt432.millennium.Millennium;
import tt432.millennium.common.recipes.base.BaseRecipe;
import tt432.millennium.common.recipes.base.Recipe;

import java.util.List;

/**
 * @author DustW
 *
 * 配方模板类，默认只会序列化 / 反序列化 @Expose 注解标注的字段
 * 使用 DataGenerator 时请注意 type 字段需要手动填充，否则 mc 的配方系统无法找到对应的解析器
 **/
@Recipe(Millennium.ID + ":test")
public class TemplateRecipe extends BaseRecipe<CraftingContainer> implements CraftingRecipe {

    @Expose NonNullList<Ingredient> inputs;
    @Expose ItemStack result;

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public boolean matches(List<ItemStack> inputs) {
        return RecipeMatcher.findMatches(inputs, this.inputs) != null;
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }
}
