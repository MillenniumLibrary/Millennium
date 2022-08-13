package tt432.millennium.common.recipes.ingredients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 匹配 nbt
 * @author DustW
 **/
public class NbtIngredient implements Predicate<Tag> {

    @Expose
    @SerializedName("nbt_list")
    List<SingleNbt> nbtList;
    @Expose
    boolean all;

    public NbtIngredient(SingleNbt... nbtList) {
        this.nbtList = List.of(nbtList);
    }

    public NbtIngredient(boolean all, SingleNbt... nbtList) {
        this(nbtList);
        this.all = all;
    }

    @FunctionalInterface
    private interface Tester {
        boolean test(Object value1, Object value2);
    }

    @Override
    public boolean test(Tag tag) {
        if (all) {
            return nbtList.stream().allMatch(nbt -> nbt.test(tag));
        }
        else {
            return nbtList.stream().anyMatch(nbt -> nbt.test(tag));
        }
    }

    public static class SingleNbt implements Predicate<Tag> {

        @Expose String path;
        @Expose String key;
        @Expose MatchMode mode;
        @Expose Object value;
        @Nullable @Expose NbtIngredient child;

        List<String> pathList;

        public SingleNbt(String path, String key, MatchMode mode, Object value, @Nullable NbtIngredient child) {
            this.path = path;
            this.key = key;
            this.mode = mode;
            this.value = value;
            this.child = child;
        }

        @Override
        public boolean test(Tag tag) {
            if (key == null) {
                // TODO 错误信息
                throw new IllegalArgumentException();
            }

            if (pathList == null) {
                if (path != null && !path.isEmpty()) {
                    pathList = List.of(path.split("/"));
                } else {
                    pathList = List.of();
                }
            }

            if (tag instanceof CompoundTag compoundTag) {
                for (String s : pathList) {
                    if (compoundTag.contains(s)) {
                        try {
                            compoundTag = compoundTag.getCompound(s);
                        } catch (Exception e) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }

                return compoundTag.contains(key) && (
                        compoundTag.getTagType(key) == Tag.TAG_LIST ?
                                child != null &&
                                        ((ListTag) Objects.requireNonNull(compoundTag.get(key)))
                                                .stream().anyMatch(c -> child.test(c)) :
                                mode.test(value, compoundTag.get(key)));
            }
            else {
                return mode.test(value, tag);
            }
        }
    }

    public enum MatchMode {
        /** 超过 */
        MORE((o1, o2) -> {
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

        MatchMode(Tester tester) {
            this.tester = tester;
        }

        public boolean test(Object value1, Object value2) {
            return tester.test(value1, value2);
        }
    }
}
