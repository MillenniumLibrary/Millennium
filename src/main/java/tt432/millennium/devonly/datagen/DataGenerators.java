package tt432.millennium.devonly.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import tt432.millennium.Millennium;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(modid = DataGenerators.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    public static final String MOD_ID = Millennium.ID;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (!Millennium.DEV_MODE) {
            return;
        }

        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new TutRecipes(generator));
            generator.addProvider(new TutLootTables(generator));
            TutBlockTags blockTags = new TutBlockTags(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new TutItemTags(generator, blockTags, event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
            // generator.addProvider(new TutBlockStates(generator, event.getExistingFileHelper()));
            // generator.addProvider(new TutItemModels(generator, event.getExistingFileHelper()));
        }
    }
}