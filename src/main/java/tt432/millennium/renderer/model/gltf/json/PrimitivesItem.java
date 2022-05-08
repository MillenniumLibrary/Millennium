package tt432.millennium.renderer.model.gltf.json;

import com.google.gson.annotations.SerializedName;

public class PrimitivesItem{

	@SerializedName("indices")
	private int indices;

	@SerializedName("attributes")
	private Attributes attributes;

	public int getIndices(){
		return indices;
	}

	public Attributes getAttributes(){
		return attributes;
	}
}