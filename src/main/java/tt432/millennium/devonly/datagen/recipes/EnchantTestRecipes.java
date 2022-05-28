package tt432.millennium.devonly.datagen.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import tt432.millennium.devonly.recipe.recipe.EnchantTestRecipe;
import tt432.millennium.recipes.ingredients.ExItemIngredient;
import tt432.millennium.recipes.ingredients.NbtIngredient;
import tt432.millennium.utils.json.JsonUtil;

import java.util.Arrays;

/**
 * @author DustW
 **/
public class EnchantTestRecipes extends DataGenRecipes {
    @Override
    protected void addRecipes() {
        addEnchantTestRecipe(new EnchantTestRecipe(Arrays.asList(
                new ExItemIngredient(
                        new ExItemIngredient.SingleExItem(Items.IRON_SWORD, 1, new NbtIngredient(
                                new NbtIngredient.SingleNbt("", "Enchantments", null, null,
                                        new NbtIngredient(
                                                true,
                                                new NbtIngredient.SingleNbt("", "id",
                                                        NbtIngredient.MatchMode.EQUAL,
                                                        Enchantments.SHARPNESS.getRegistryName().toString(), null),
                                                new NbtIngredient.SingleNbt("", "lvl",
                                                        NbtIngredient.MatchMode.MORE, 1, null),
                                                new NbtIngredient.SingleNbt("", "lvl",
                                                        NbtIngredient.MatchMode.LESS, 4, null)
                                        ))
                        ))
                )
        ), new ItemStack(Items.GLASS_BOTTLE)));
    }

    protected void addEnchantTestRecipe(EnchantTestRecipe recipe) {
        addRecipe(defaultName(recipe.getResultItem().getItem()), JsonUtil.INSTANCE.pretty.toJson(recipe), "smithing");
    }
}
