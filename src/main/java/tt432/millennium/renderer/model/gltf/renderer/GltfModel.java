package tt432.millennium.renderer.model.gltf.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import tt432.millennium.renderer.model.gltf.json.GltfJson;
import tt432.millennium.renderer.model.gltf.json.NodesItem;
import tt432.millennium.utils.json.JsonUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class GltfModel {
    GltfJson json;

    List<Node> nodes;
    List<Buffer> buffers;
    List<BufferView> bufferViews;
    List<Accessor> accessors;

    public void init(ResourceLocation location) throws IOException {
        var resourceManager = Minecraft.getInstance().getResourceManager();
        var inputStream = resourceManager.getResource(location).getInputStream();

        json = JsonUtils.INSTANCE.noExpose.fromJson(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8), GltfJson.class);

        nodes = new ArrayList<>();

        for (NodesItem node : json.getNodes()) {
            nodes.add(new Node(node));
        }

        buffers = new ArrayList<>();

        for (var buffer : json.getBuffers()) {
            var bf = new Buffer(buffer);
            bf.init(location);
            buffers.add(bf);
        }

        bufferViews = new ArrayList<>();

        for (var bufferView : json.getBufferViews()) {
            bufferViews.add(new BufferView(bufferView));
        }

        accessors = new ArrayList<>();

        for (var accessor : json.getAccessors()) {
            accessors.add(new Accessor(this, accessor));
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Buffer> getBuffers() {
        return buffers;
    }

    public List<BufferView> getBufferViews() {
        return bufferViews;
    }

    public List<Accessor> getAccessors() {
        return accessors;
    }
}
