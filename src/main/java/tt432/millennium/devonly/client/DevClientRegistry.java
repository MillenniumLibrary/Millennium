package tt432.millennium.devonly.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.timlee9024.mcgltf.MCglTF;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tt432.millennium.Millennium;
import tt432.millennium.devonly.AllInOneObject;
import tt432.millennium.devonly.ModelBlockEntity;
import tt432.millennium.renderer.model.GltfModels;
import tt432.millennium.renderer.model.gltf.BaseModelBlockEntityRenderer;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DevClientRegistry {
    @SubscribeEvent
    public static void onEvent(final EntityRenderersEvent.RegisterRenderers event) {
        if (Millennium.DEV_MODE) {
            for (AllInOneObject allInOneObject : AllInOneObject.list) {
                event.registerBlockEntityRenderer(allInOneObject.beType.get(), (c) -> {
                    var bbr = new BaseModelBlockEntityRenderer(allInOneObject.model) {
                        @Override
                        public void renderInner(ModelBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

                        }
                    };

                    MCglTF.getInstance().addGltfModelReceiver(bbr);

                    return bbr;
                });
            }
        }
    }
}
