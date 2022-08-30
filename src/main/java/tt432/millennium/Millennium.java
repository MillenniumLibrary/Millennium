package tt432.millennium;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tt432.millennium.auto.RegDRLogic;

/**
 * @author DustW
 */
@Mod(Millennium.ID)
public class Millennium {
    public static final String ID = "millennium";

    public Millennium() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        RegDRLogic.register(bus);
    }
}
