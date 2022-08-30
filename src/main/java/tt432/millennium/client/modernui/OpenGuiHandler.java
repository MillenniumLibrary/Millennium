package tt432.millennium.client.modernui;

import icyllis.modernui.forge.OpenMenuEvent;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import tt432.millennium.auto.RegUILogic;

import javax.annotation.Nonnull;

/**
 * @author DustW
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenGuiHandler {
    public static void openMenu(@Nonnull OpenMenuEvent event) {
        AbstractContainerMenu menu = event.getMenu();

        if (RegUILogic.isUIMenu(menu)) {
            event.set(setBaseArguments(RegUILogic.create(menu), menu));
        }
    }

    static Fragment setBaseArguments(Fragment fragment, AbstractContainerMenu menu) {
        fragment.setArguments(baseDataSet(menu));
        return fragment;
    }

    static DataSet baseDataSet(AbstractContainerMenu menu) {
        DataSet args = new DataSet();
        args.putInt("token", menu.containerId);
        return args;
    }
}
