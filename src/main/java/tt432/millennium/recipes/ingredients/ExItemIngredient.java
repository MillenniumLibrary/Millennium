package tt432.millennium.recipes.ingredients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author DustW
 **/
public class ExItemIngredient implements Predicate<ItemStack> {

    public static class SingleExItem implements Predicate<ItemStack> {

        private Item itemCache;
        private TagKey<Item> tagCache;
        private boolean tag;
        private boolean cached;

        @Expose
        @SerializedName("item_name")
        public String itemName;
        @Expose
        public int amount;
        @Expose
        @SerializedName("nbt")
        public NbtIngredient nbt;

        public SingleExItem(String itemName, int amount) {
            this.itemName = itemName;
            this.amount = amount;
        }

        public SingleExItem(Item item, int amount) {
            this(item.getRegistryName().toString(), amount);
            tag = false;
            itemCache = item;
        }

        public SingleExItem(ItemStack itemStack) {
            this(itemStack.getItem(), itemStack.getCount());
        }

        public SingleExItem(TagKey<Item> tag, int amount) {
            this("#" + tag.location(), amount);
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

                cached = true;
            }
        }
    }

    @Expose
    @SerializedName("items")
    public List<SingleExItem> singleExItems;
    @Expose
    boolean all;

    public ExItemIngredient(SingleExItem... fluids) {
        this.singleExItems = List.of(fluids);
    }

    @Override
    public boolean test(ItemStack input) {
        if (all) {
            return singleExItems.stream().allMatch(item -> item.test(input));
        }
        else {
            return singleExItems.stream().anyMatch(item -> item.test(input));
        }
    }
}
