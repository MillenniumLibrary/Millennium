package tt432.millennium.data.recipe;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author DustW
 **/
public abstract class AbstractRecipeProvider extends RecipeProvider {

    protected AbstractRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected List<AbstractRecipeSet> recipes = new ArrayList<>();

    protected abstract void addCustomRecipes();

    protected abstract void vanillaRecipes(Consumer<FinishedRecipe> consumer);

    void smelting(Consumer<FinishedRecipe> consumer, Item input, Item output) {
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(input),
                output,
                .3F,
                200
        ).unlockedBy("has_stone", has(Blocks.DIORITE)).save(consumer);
    }

    @Override
    public void run(@NotNull HashCache cache) {
        super.run(cache);

        recipes.forEach(recipe -> recipe.getRecipes().forEach((name, entry) -> save(cache, name, entry)));
    }

    protected void save(HashCache pCache, ResourceLocation name, Map.Entry<String, String> entry) {
        String json = entry.getKey();
        String subPath = entry.getValue();

        Path path = this.generator.getOutputFolder();

        saveRecipe(pCache, json, path.resolve("data/" + name.getNamespace() + "/recipes/" + subPath + "/" + name.getPath() + ".json"));
    }

    private static void saveRecipe(HashCache pCache, String recipe, Path pPath) {
        try {
            String s1 = SHA1.hashUnencodedChars(recipe).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
                    bufferedwriter.write(recipe);
                }
            }

            pCache.putNew(pPath, s1);
        } catch (IOException ignored) {
            // do nothing
        }
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        addCustomRecipes();
        vanillaRecipes(consumer);
    }
}
