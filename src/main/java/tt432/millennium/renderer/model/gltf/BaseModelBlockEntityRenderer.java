package tt432.millennium.renderer.model.gltf;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.timlee9024.mcgltf.IGltfModelReceiver;
import com.timlee9024.mcgltf.MCglTF;
import com.timlee9024.mcgltf.RenderedGltfModel;
import de.javagl.jgltf.model.GltfAnimations;
import de.javagl.jgltf.model.animation.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.*;
import tt432.millennium.devonly.ModelBlockEntity;

/**
 * @author DustW
 **/
public class BaseModelBlockEntityRenderer extends BaseGltfModelReceiver implements BlockEntityRenderer<ModelBlockEntity> {
    public BaseModelBlockEntityRenderer(ResourceLocation model) {
        super(model);
    }

    @Override
    public boolean shouldRenderOffScreen(ModelBlockEntity pBlockEntity) {
        return true;
    }

    IGltfModelReceiver receiver;

    public IGltfModelReceiver getModel() {
        return receiver == null ? receiver = new IGltfModelReceiver() {
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
        } : receiver;
    }

    public void renderInner(ModelBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

    }

    @Override
    public void render(ModelBlockEntity pBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource pBufferSource, int packedLight, int packedOverlay) {
        Minecraft mc = Minecraft.getInstance();

        if (animations != null) {
            for (Animation animation : animations) {
                animation.update((mc.level.getGameTime() + partialTick) / 20 % animation.getEndTimeS());
            }
        }

        int currentVAO = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING);
        int currentArrayBuffer = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
        int currentElementArrayBuffer = GL11.glGetInteger(GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING);

        boolean currentCullFace = GL11.glGetBoolean(GL11.GL_CULL_FACE);

        boolean currentDepthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
        boolean currentBlend = GL11.glGetBoolean(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        poseStack.pushPose();
        // 很好
        renderInner(pBlockEntity, partialTick, poseStack, pBufferSource, packedLight, packedOverlay);

        MCglTF.CURRENT_POSE = poseStack.last().pose();
        MCglTF.CURRENT_NORMAL = poseStack.last().normal();
        poseStack.popPose();

        var light = LightTexture.FULL_BRIGHT;

        GL30.glVertexAttribI2i(RenderedGltfModel.vaUV2, light & '\uffff', packedOverlay >> 16 & '\uffff');

        MCglTF.CURRENT_PROGRAM = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        if(MCglTF.CURRENT_PROGRAM == 0) {
            renderWithVanillaCommands();
            GL20.glUseProgram(0);
        }
        else {
            MCglTF.MODEL_VIEW_MATRIX = GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "modelViewMatrix");
            if(MCglTF.MODEL_VIEW_MATRIX == -1) {
                int currentProgram = MCglTF.CURRENT_PROGRAM;
                renderWithVanillaCommands();
                GL20.glUseProgram(currentProgram);
            }
            else {
                MCglTF.MODEL_VIEW_MATRIX_INVERSE = GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "modelViewMatrixInverse");
                MCglTF.NORMAL_MATRIX = GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "normalMatrix");

                RenderSystem.getProjectionMatrix().store(MCglTF.BUF_FLOAT_16);
                GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "projectionMatrix"), false, MCglTF.BUF_FLOAT_16);
                Matrix4f projectionMatrixInverse = RenderSystem.getProjectionMatrix().copy();
                projectionMatrixInverse.invert();
                projectionMatrixInverse.store(MCglTF.BUF_FLOAT_16);
                GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(MCglTF.CURRENT_PROGRAM, "projectionMatrixInverse"), false, MCglTF.BUF_FLOAT_16);

                GL13.glActiveTexture(GL13.GL_TEXTURE3);
                int currentTexture3 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
                GL13.glActiveTexture(GL13.GL_TEXTURE1);
                int currentTexture1 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                int currentTexture0 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

                shaderModCommands.forEach(Runnable::run);

                GL13.glActiveTexture(GL13.GL_TEXTURE3);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture3);
                GL13.glActiveTexture(GL13.GL_TEXTURE1);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture1);
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture0);
            }
        }

        GL30.glVertexAttribI2i(RenderedGltfModel.vaUV2, 0, 0);

        if(!currentDepthTest) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
        }
        if(!currentBlend) {
            GL11.glDisable(GL11.GL_BLEND);
        }

        if(currentCullFace) {
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
        else {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }

        GL30.glBindVertexArray(currentVAO);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, currentArrayBuffer);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, currentElementArrayBuffer);
    }
}
