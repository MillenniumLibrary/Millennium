package tt432.millennium.recipes.ingredients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.tags.ITag;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author DustW
 **/
public class NbtIngredient implements Predicate<CompoundTag> {

    public static class SingleNbt implements Predicate<CompoundTag> {

        @Expose
        String path;
        @Expose
        String key;
        @Expose
        @SerializedName("match_mode")
        Mode matchMode;
        @Expose
        Object value;

        List<String> pathList;

        @Override
        public boolean test(CompoundTag compoundTag) {
            if (pathList == null) {
                pathList = List.of(path.split("/"));
            }

            for (String s : pathList) {
                if (compoundTag.contains(s)) {
                    try {
                        compoundTag = compoundTag.getCompound(s);
                    }
                    catch (Exception e) {
                        LogManager.getLogger().error(new TranslatableComponent("error.NbtIngredient.path", compoundTag.toString()));
                        return false;
                    }
                }
                else {
                    return false;
                }
            }

            return compoundTag.contains(key) && matchMode.test(value, compoundTag.get(key));
        }
    }

    public enum Mode {
        /** 超过 */
        MODE((o1, o2) -> {
            if (o1 instanceof Number number && o2 instanceof NumericTag numericTag) {
                return numericTag.getAsNumber().doubleValue() > number.doubleValue();
            }

            return false;
        }),
        /** 少于 */
        LESS((o1, o2) -> {
            if (o1 instanceof Number number && o2 instanceof NumericTag numericTag) {
                return numericTag.getAsNumber().doubleValue() < number.doubleValue();
            }

            return false;
        }),
        /** 等于 */
        EQUAL((o1, o2) -> {
            if (o1 instanceof Number number && o2 instanceof NumericTag numericTag) {
                return numericTag.getAsNumber().equals(number);
            }
            else if (o1 instanceof String && o2 instanceof StringTag stringTag) {
                return o1.equals(stringTag.getAsString());
            }

            return false;
        }),
        /** 不等于 */
        NOT_EQUAL((o1, o2) -> !EQUAL.test(o1, o2)),
        /** 包含 */
        INCLUDE((o1, o2) -> {
            if (o2 instanceof ListTag listTag) {
                return listTag.stream().anyMatch(tag -> EQUAL.test(o1, tag));
            }

            return false;
        }),
        /** 不含 */
        NOT_INCLUDED((o1, o2) -> !INCLUDE.test(o1, o2));

        Tester tester;

        Mode(Tester tester) {
            this.tester = tester;
        }

        public boolean test(Object value1, Object value2) {
            return tester.test(value1, value2);
        }
    }

    @Expose
    @SerializedName("nbt_list")
    List<SingleNbt> nbtList;
    @Expose
    boolean all;

    public NbtIngredient(SingleNbt... nbtList) {
        this.nbtList = List.of(nbtList);
    }

    @FunctionalInterface
    private interface Tester {
        boolean test(Object value1, Object value2);
    }

    @Override
    public boolean test(CompoundTag tag) {
        if (all) {
            return nbtList.stream().allMatch(nbt -> nbt.test(tag));
        }
        else {
            return nbtList.stream().anyMatch(nbt -> nbt.test(tag));
        }
    }
}
