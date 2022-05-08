package tt432.millennium.renderer.model.gltf.json;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GltfJson{

	@SerializedName("nodes")
	private List<NodesItem> nodes;

	@SerializedName("accessors")
	private List<AccessorsItem> accessors;

	@SerializedName("buffers")
	private List<BuffersItem> buffers;

	@SerializedName("scenes")
	private List<ScenesItem> scenes;

	@SerializedName("bufferViews")
	private List<BufferViewsItem> bufferViews;

	@SerializedName("asset")
	private Asset asset;

	@SerializedName("meshes")
	private List<MeshesItem> meshes;

	@SerializedName("scene")
	private int scene;

	public List<NodesItem> getNodes(){
		return nodes;
	}

	public List<AccessorsItem> getAccessors(){
		return accessors;
	}

	public List<BuffersItem> getBuffers(){
		return buffers;
	}

	public List<ScenesItem> getScenes(){
		return scenes;
	}

	public List<BufferViewsItem> getBufferViews(){
		return bufferViews;
	}

	public Asset getAsset(){
		return asset;
	}

	public List<MeshesItem> getMeshes(){
		return meshes;
	}

	public int getScene(){
		return scene;
	}
}