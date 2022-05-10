package tt432.millennium;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tt432.millennium.devonly.DevRegistry;

import java.util.stream.Collectors;

/**
 * @author DustW
 */
@Mod(Millennium.MOD_ID)
public class Millennium {
    public static final String MOD_ID = "millennium";

    /** 用来控制某些东西只在开发环境出现 */
    public static final boolean DEV_MODE = true;

    public Millennium() {
        if (DEV_MODE) {
            DevRegistry.register();
        }
    }
}
