package tt432.millennium;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import tt432.millennium.devonly.DevRegistry;

/**
 * @author DustW
 */
@Mod(Millennium.ID)
public class Millennium {
    public static final String ID = "millennium";

    /** 用来控制某些东西只在开发环境出现 */
    public static final boolean DEV_MODE = false;

    public static boolean MC_GLTF_LOAD;

    public Millennium() {
        MC_GLTF_LOAD = ModList.get().isLoaded("mcgltf");

        if (DEV_MODE) {
            DevRegistry.register();
        }
    }
}
