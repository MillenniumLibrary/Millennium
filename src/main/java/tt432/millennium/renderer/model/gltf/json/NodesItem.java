package tt432.millennium.renderer.model.gltf.json;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NodesItem{

	@SerializedName("rotation")
	private List<Integer> rotation;

	@SerializedName("name")
	private String name;

	@SerializedName("translation")
	private List<Double> translation;

	@SerializedName("scale")
	private List<Integer> scale;

	@SerializedName("mesh")
	private int mesh;

	public List<Integer> getRotation(){
		return rotation;
	}

	public String getName(){
		return name;
	}

	public List<Double> getTranslation(){
		return translation;
	}

	public List<Integer> getScale(){
		return scale;
	}

	public int getMesh(){
		return mesh;
	}
}