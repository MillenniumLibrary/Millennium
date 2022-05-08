package tt432.millennium.renderer.model.gltf.json;

import com.google.gson.annotations.SerializedName;

public class BufferViewsItem{

	@SerializedName("byteOffset")
	private int byteOffset;

	@SerializedName("byteLength")
	private int byteLength;

	@SerializedName("buffer")
	private int buffer;

	public int getByteOffset(){
		return byteOffset;
	}

	public int getByteLength(){
		return byteLength;
	}

	public int getBuffer(){
		return buffer;
	}
}