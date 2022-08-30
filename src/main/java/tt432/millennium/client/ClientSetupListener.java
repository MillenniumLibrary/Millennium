package tt432.millennium.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tt432.millennium.auto.RegUILogic;
import tt432.millennium.client.markdown.MarkdownManager;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupListener {
    @SubscribeEvent
    public static void onEvent(FMLClientSetupEvent event) {
        RegUILogic.register();
    }

    @SubscribeEvent
    public static void onEvent(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(MarkdownManager.INSTANCE);
    }
}
