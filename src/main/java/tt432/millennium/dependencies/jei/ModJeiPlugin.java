package tt432.millennium.dependencies.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.common.gui.GuiProperties;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import tt432.millennium.Millennium;
import tt432.millennium.api.client.modernui.IMenuScreen;
import tt432.millennium.client.modernui.BaseFragment;

/**
 * @author DustW
 **/
@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(Millennium.ID, "plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        try {
            Class<? extends AbstractContainerScreen<?>> screen =
                    (Class<? extends AbstractContainerScreen<?>>) Class.forName("icyllis.modernui.forge.MenuScreen");
            registration.addGuiScreenHandler(screen, this::get);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private IGuiProperties get(AbstractContainerScreen<?> guiScreen) {
        var fragment = ((IMenuScreen) guiScreen).getFragmentImi();

        if (fragment instanceof BaseFragment b && b.closeJei()) {
            return null;
        }

        return GuiProperties.create(guiScreen);
    }
}
