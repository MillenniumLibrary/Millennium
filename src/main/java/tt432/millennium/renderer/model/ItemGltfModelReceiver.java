package tt432.millennium.renderer.model;

import com.timlee9024.mcgltf.IGltfModelReceiver;
import com.timlee9024.mcgltf.RenderedGltfModel;
import de.javagl.jgltf.model.GltfAnimations;
import de.javagl.jgltf.model.animation.Animation;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * @author DustW
 **/
public class ItemGltfModelReceiver implements IGltfModelReceiver {

    private final ResourceLocation model;

    public Runnable vanillaSkinningCommands;

    public List<Runnable> vanillaRenderCommands;

    public List<Runnable> shaderModCommands;

    public List<Animation> animations;

    public ItemGltfModelReceiver(ResourceLocation model) {
        this.model = model;
    }

    @Override
    public ResourceLocation getModelLocation() {
        return model;
    }

    @Override
    public void onModelLoaded(RenderedGltfModel renderedModel) {
        vanillaSkinningCommands = renderedModel.vanillaSceneSkinningCommands.get(0);
        vanillaRenderCommands = renderedModel.vanillaSceneRenderCommands.get(0);
        shaderModCommands = renderedModel.shaderModSceneCommands.get(0);
        animations = GltfAnimations.createModelAnimations(renderedModel.gltfModel.getAnimationModels());
    }
}
