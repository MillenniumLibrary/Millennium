package tt432.millennium.devonly.recipe.recipe;

import com.google.gson.annotations.Expose;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tt432.millennium.Millennium;
import tt432.millennium.devonly.DevRegistry;
import tt432.millennium.recipes.base.BaseRecipe;
import tt432.millennium.recipes.ingredients.EffectIngredient;
import tt432.millennium.recipes.ingredients.EntityIngredient;
import tt432.millennium.recipes.stack.EntityStack;

import java.util.List;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber
public class BioTransformationRecipe<I extends LivingEntity, O extends LivingEntity> extends BaseRecipe.Base {

    @Expose private EntityIngredient<I> input;
    @Expose private List<EffectIngredient> effect;

    @Expose private EntityStack<O> output;

    public BioTransformationRecipe(EntityIngredient<I> input, List<EffectIngredient> effect, EntityStack<O> output) {
        this.input = input;
        this.effect = effect;
        this.output = output;

        type = Millennium.MOD_ID + ":bio";
    }

    @SubscribeEvent
    public static void onEvent(PotionEvent.PotionAddedEvent event) {
        if (Millennium.DEV_MODE) {
            var entity = event.getEntityLiving();
            var level = entity.level;

            if (level.isClientSide) {
                return;
            }

            var recipeOptional = level.getRecipeManager()
                    .getAllRecipesFor(DevRegistry.BIO.get()).stream().filter(r -> r.match(entity)).findFirst();

            if (recipeOptional.isPresent()) {
                var recipe = recipeOptional.get();
                var newEntity = recipe.getOutput().get().create(level);

                if (newEntity == null) {
                    return;
                }

                var tag = new CompoundTag();
                entity.save(tag);
                newEntity.load(tag);
                entity.remove(Entity.RemovalReason.DISCARDED);
                level.addFreshEntity(newEntity);
            }
        }
    }

    public boolean match(LivingEntity entity) {
        return input.test((I) entity) && RecipeMatcher.findMatches(entity.getActiveEffects().stream().toList(), effect) != null;
    }

    public EntityStack<O> getOutput() {
        return output;
    }

    @Override
    public boolean matches(List<ItemStack> inputs) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DevRegistry.BIO_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DevRegistry.BIO.get();
    }
}
