package tt432.millennium.common.recipes.ingredients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tt432.millennium.util.json.JsonUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * 添加了 nbt 扩展的 Ingredient
 * @see net.minecraft.world.item.crafting.Ingredient
 * @author DustW
 **/
public class ExItemIngredient extends AbstractIngredient {

    @Expose
    @SerializedName("items")
    public List<SingleExItem> singleExItems;

    public ExItemIngredient(SingleExItem... items) {
        super(Arrays.stream(items));
        this.singleExItems = List.of(items);
    }

    public void shrink(ItemStack itemStack) {
        for (SingleExItem singleExItem : singleExItems) {
            if (singleExItem.test(itemStack)) {
                itemStack.shrink(singleExItem.amount);
                break;
            }
        }
    }

    @Override
    public void dissolve() {
        if (this.itemStacks == null) {
            this.itemStacks = this.singleExItems.stream().flatMap((value) -> value.getItems().stream())
                    .distinct().toArray(ItemStack[]::new);
        }
    }

    @Override
    public boolean test(ItemStack input) {
        return singleExItems.stream().anyMatch(item -> item.test(input));
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return new IIngredientSerializer<ExItemIngredient>() {
            @Override
            public ExItemIngredient parse(FriendlyByteBuf buffer) {
                return JsonUtils.INSTANCE.normal.fromJson(buffer.readUtf(), ExItemIngredient.class);
            }

            @Override
            public ExItemIngredient parse(JsonObject json) {
                return JsonUtils.INSTANCE.normal.fromJson(json, ExItemIngredient.class);
            }

            @Override
            public void write(FriendlyByteBuf buffer, ExItemIngredient ingredient) {
                buffer.writeUtf(JsonUtils.INSTANCE.normal.toJson(ingredient));
            }
        };
    }

    @Override
    public JsonElement toJson() {
        return JsonUtils.INSTANCE.normal.toJsonTree(this);
    }

    public static class SingleExItem implements Predicate<ItemStack>, Ingredient.Value {

        private Item itemCache;
        private TagKey<Item> tagCache;
        private boolean tag;
        private boolean cached;

        @Expose
        @SerializedName("item")
        public String itemName;
        @Expose
        public int amount;
        @Expose
        @Nullable
        public NbtIngredient nbt;

        public SingleExItem(String itemName, int amount, @Nullable NbtIngredient nbt) {
            this.itemName = itemName;
            this.amount = amount;
            this.nbt = nbt;
        }

        public SingleExItem(Item item, int amount, @Nullable NbtIngredient nbt) {
            this(item.getRegistryName().toString(), amount, nbt);
            tag = false;
            itemCache = item;
        }

        public SingleExItem(ItemStack itemStack, @Nullable NbtIngredient nbt) {
            this(itemStack.getItem(), itemStack.getCount(), nbt);
        }

        public SingleExItem(TagKey<Item> tag, int amount, @Nullable NbtIngredient nbt) {
            this("#" + tag.location(), amount, nbt);
            this.tag = true;
            tagCache = tag;
        }

        @Override
        public boolean test(ItemStack stack) {
            cache();

            boolean result;

            if (tag) {
                result = stack.is(tagCache) && stack.getCount() >= amount;
            }
            else {
                result = stack.getItem() == itemCache && stack.getCount() >= amount;
            }

            if (result) {
                result = (nbt == null) || (stack.getTag() != null && nbt.test(stack.getTag()));
            }

            return result;
        }

        private void cache() {
            if (!cached) {
                if (itemName.startsWith("#")) {
                    tag = true;
                    tagCache = ItemTags.create(new ResourceLocation(itemName.substring(1)));
                }
                else {
                    itemCache = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
                }

                value = tag ? new Ingredient.TagValue(tagCache) :
                        new Ingredient.ItemValue(new ItemStack(itemCache));

                cached = true;
            }
        }

        Ingredient.Value value;

        @Override
        public Collection<ItemStack> getItems() {
            cache();

            return value.getItems();
        }

        @Override
        public JsonObject serialize() {
            return JsonUtils.INSTANCE.normal.toJsonTree(this).getAsJsonObject();
        }
    }
}
