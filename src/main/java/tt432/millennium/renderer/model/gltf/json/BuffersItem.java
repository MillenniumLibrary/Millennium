package tt432.millennium.renderer.model.gltf.json;

import com.google.gson.annotations.SerializedName;

public class BuffersItem {

	@SerializedName("byteLength")
	private int byteLength;

	@SerializedName("uri")
	private String uri;

	public int getByteLength(){
		return byteLength;
	}

	public String getUri(){
		return uri;
	}
}