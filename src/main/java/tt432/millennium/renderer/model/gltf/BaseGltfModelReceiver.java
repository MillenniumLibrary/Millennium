package tt432.millennium.renderer.model.gltf;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import com.timlee9024.mcgltf.IGltfModelReceiver;
import com.timlee9024.mcgltf.MCglTF;
import com.timlee9024.mcgltf.RenderedGltfModel;
import de.javagl.jgltf.model.GltfAnimations;
import de.javagl.jgltf.model.animation.Animation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import java.util.List;

/**
 * @author DustW
 **/
public class BaseGltfModelReceiver implements IGltfModelReceiver {

    protected final ResourceLocation model;

    public Runnable vanillaSkinningCommands;

    public List<Runnable> vanillaRenderCommands;

    public List<Runnable> shaderModCommands;

    public List<Animation> animations;

    public BaseGltfModelReceiver(ResourceLocation model) {
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

    protected void renderWithVanillaCommands() {
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        int currentTexture2 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, MCglTF.getInstance().getLightTexture().getId());

        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        int currentTexture1 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        int currentTexture0 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);


        if (vanillaSkinningCommands != null) {
            vanillaSkinningCommands.run();
        }

        MCglTF.CURRENT_SHADER_INSTANCE = GameRenderer.getRendertypeEntitySolidShader();
        MCglTF.CURRENT_PROGRAM = MCglTF.CURRENT_SHADER_INSTANCE.getId();
        GL20.glUseProgram(MCglTF.CURRENT_PROGRAM);

        MCglTF.CURRENT_SHADER_INSTANCE.PROJECTION_MATRIX.set(RenderSystem.getProjectionMatrix());
        MCglTF.CURRENT_SHADER_INSTANCE.PROJECTION_MATRIX.upload();

        MCglTF.CURRENT_SHADER_INSTANCE.INVERSE_VIEW_ROTATION_MATRIX.set(RenderSystem.getInverseViewRotationMatrix());
        MCglTF.CURRENT_SHADER_INSTANCE.INVERSE_VIEW_ROTATION_MATRIX.upload();

        MCglTF.CURRENT_SHADER_INSTANCE.FOG_START.set(RenderSystem.getShaderFogStart());
        MCglTF.CURRENT_SHADER_INSTANCE.FOG_START.upload();

        MCglTF.CURRENT_SHADER_INSTANCE.FOG_END.set(RenderSystem.getShaderFogEnd());
        MCglTF.CURRENT_SHADER_INSTANCE.FOG_END.upload();

        MCglTF.CURRENT_SHADER_INSTANCE.FOG_COLOR.set(RenderSystem.getShaderFogColor());
        MCglTF.CURRENT_SHADER_INSTANCE.FOG_COLOR.upload();

        MCglTF.CURRENT_SHADER_INSTANCE.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
        MCglTF.CURRENT_SHADER_INSTANCE.FOG_SHAPE.upload();

        GL20.glUniform1i(GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "Sampler0"), 0);
        GL20.glUniform1i(GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "Sampler1"), 1);
        GL20.glUniform1i(GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "Sampler2"), 2);

        RenderSystem.setupShaderLights(MCglTF.CURRENT_SHADER_INSTANCE);
        MCglTF.LIGHT0_DIRECTION = new Vector3f(MCglTF.CURRENT_SHADER_INSTANCE.LIGHT0_DIRECTION.getFloatBuffer().get(0), MCglTF.CURRENT_SHADER_INSTANCE.LIGHT0_DIRECTION.getFloatBuffer().get(1), MCglTF.CURRENT_SHADER_INSTANCE.LIGHT0_DIRECTION.getFloatBuffer().get(2));
        MCglTF.LIGHT1_DIRECTION = new Vector3f(MCglTF.CURRENT_SHADER_INSTANCE.LIGHT1_DIRECTION.getFloatBuffer().get(0), MCglTF.CURRENT_SHADER_INSTANCE.LIGHT1_DIRECTION.getFloatBuffer().get(1), MCglTF.CURRENT_SHADER_INSTANCE.LIGHT1_DIRECTION.getFloatBuffer().get(2));

        if (vanillaRenderCommands != null) {
            vanillaRenderCommands.forEach(Runnable::run);
        }

        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture2);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture0);
    }
}
