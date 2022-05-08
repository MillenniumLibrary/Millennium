package tt432.millennium.renderer.model.gltf.json;

import com.google.gson.annotations.SerializedName;

public class Asset{

	@SerializedName("generator")
	private String generator;

	@SerializedName("version")
	private String version;

	public String getGenerator(){
		return generator;
	}

	public String getVersion(){
		return version;
	}
}