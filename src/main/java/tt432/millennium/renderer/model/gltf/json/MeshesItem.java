package tt432.millennium.renderer.model.gltf.json;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MeshesItem{

	@SerializedName("primitives")
	private List<PrimitivesItem> primitives;

	@SerializedName("name")
	private String name;

	public List<PrimitivesItem> getPrimitives(){
		return primitives;
	}

	public String getName(){
		return name;
	}
}