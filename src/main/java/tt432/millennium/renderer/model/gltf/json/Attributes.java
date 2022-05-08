package tt432.millennium.renderer.model.gltf.json;

import com.google.gson.annotations.SerializedName;

public class Attributes{

	@SerializedName("POSITION")
	private int pOSITION;

	@SerializedName("TEXCOORD_0")
	private int tEXCOORD0;

	@SerializedName("NORMAL")
	private int nORMAL;

	public int getPOSITION(){
		return pOSITION;
	}

	public int getTEXCOORD0(){
		return tEXCOORD0;
	}

	public int getNORMAL(){
		return nORMAL;
	}
}