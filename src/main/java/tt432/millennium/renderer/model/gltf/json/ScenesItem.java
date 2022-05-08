package tt432.millennium.renderer.model.gltf.json;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ScenesItem{

	@SerializedName("nodes")
	private List<Integer> nodes;

	@SerializedName("name")
	private String name;

	public List<Integer> getNodes(){
		return nodes;
	}

	public String getName(){
		return name;
	}
}