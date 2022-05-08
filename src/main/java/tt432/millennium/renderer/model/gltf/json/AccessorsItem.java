package tt432.millennium.renderer.model.gltf.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccessorsItem{

	@SerializedName("componentType")
	private int componentType;

	@SerializedName("bufferView")
	private int bufferView;

	@SerializedName("count")
	private int count;

	@SerializedName("type")
	private String type;

	@SerializedName("min")
	private List<Double> min;

	@SerializedName("max")
	private List<Double> max;

	public int getComponentType(){
		return componentType;
	}

	public int getBufferView(){
		return bufferView;
	}

	public int getCount(){
		return count;
	}

	public String getType(){
		return type;
	}

	public List<Double> getMin(){
		return min;
	}

	public List<Double> getMax(){
		return max;
	}
}