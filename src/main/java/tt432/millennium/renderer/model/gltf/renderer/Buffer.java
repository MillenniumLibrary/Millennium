package tt432.millennium.renderer.model.gltf.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import tt432.millennium.renderer.model.gltf.json.BuffersItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * @author DustW
 **/
public class Buffer {
    BuffersItem json;
    byte[] bin;

    public Buffer(BuffersItem json) {
        this.json = json;
    }

    public void init(ResourceLocation gltfMain) throws IOException {
        var uri = json.getUri();

        if (uri.endsWith(".bin")) {
            var resourceManager = Minecraft.getInstance().getResourceManager();

            var mainPath = Path.of(gltfMain.getPath());

            var is = resourceManager.getResource(new ResourceLocation(gltfMain.getNamespace(),
                    mainPath.getParent().toString() + "/" + uri)).getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int ch;

            while ((ch = is.read()) != -1) {
                bos.write(ch);
            }

            bin = bos.toByteArray();

            bos.close();
        }
    }

    public byte[] getBin() {
        return bin;
    }
}
