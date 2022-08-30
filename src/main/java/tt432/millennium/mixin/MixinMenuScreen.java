package tt432.millennium.mixin;

import icyllis.modernui.fragment.Fragment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tt432.millennium.api.client.modernui.IMenuScreen;

import javax.annotation.Nonnull;

/**
 * @author DustW
 **/
@Mixin(targets = "icyllis.modernui.forge.MenuScreen", remap = false)
public abstract class MixinMenuScreen implements IMenuScreen {
    @Shadow @Nonnull public abstract Fragment getFragment();

    @NotNull
    @Override
    public Fragment getFragmentImi() {
        return getFragment();
    }
}
