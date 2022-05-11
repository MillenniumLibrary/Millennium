package tt432.millennium;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import tt432.millennium.devonly.DevRegistry;
import tt432.millennium.devonly.recipe.register.RecipeManager;

/**
 * @author DustW
 */
@Mod(Millennium.MOD_ID)
public class Millennium {
    public static final String MOD_ID = "millennium";

    /** 用来控制某些东西只在开发环境出现 */
    public static final boolean DEV_MODE = false;

    public static boolean MC_GLTF_LOAD;

    public Millennium() {
        MC_GLTF_LOAD = ModList.get().isLoaded("mcgltf");

        if (DEV_MODE) {
            DevRegistry.register();
            RecipeManager.register();
        }
    }
}
