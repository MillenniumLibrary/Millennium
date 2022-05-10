package tt432.millennium.renderer.model;

import com.timlee9024.mcgltf.MCglTF;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tt432.millennium.Millennium;

/**
 * @author DustW
 **/
public class GltfModels {
    void register() {
        ItemGltfModelReceiver itemModelReceiver = new ItemGltfModelReceiver(
                new ResourceLocation(Millennium.MOD_ID, "models/gltf/model.gltf")
        );

        MCglTF.getInstance().addGltfModelReceiver(itemModelReceiver);
    }

    // @SubscribeEvent
    // public static void onEvent(final EntityRenderersEvent.RegisterRenderers event) {
    //     event.registerBlockEntityRenderer(EXAMPLE_BLOCK_ENTITY_TYPE, (context) -> {
    //         ExampleBlockEntityRenderer ber = new ExampleBlockEntityRenderer();
    //         MCglTF.getInstance().addGltfModelReceiver(ber);
    //         return ber;
    //     });
    //     event.registerEntityRenderer(EXAMPLE_ENTITY_TYPE, (context) -> {
    //         ExampleEntityRenderer entityRenderer = new ExampleEntityRenderer(context);
    //         MCglTF.getInstance().addGltfModelReceiver(entityRenderer);
    //         return entityRenderer;
    //     });
    // }
}
