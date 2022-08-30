package tt432.millennium.client.particle.bedrock;

import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;

import java.io.Serializable;

/**
 * @author DustW
 */
public class BedrockParticle implements Serializable {
    @SerializedName("format_version")
    String formatVersion;
    @SerializedName("particle_effect")
    ParticleEffect particleEffect;

    static class ParticleEffect {
        ResourceLocation identifier;
        @SerializedName("basic_render_parameters")
        BasicRenderParameters renderParams;

        static class BasicRenderParameters {

        }
    }
}
