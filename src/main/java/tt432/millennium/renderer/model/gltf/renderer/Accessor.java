package tt432.millennium.renderer.model.gltf.renderer;

import tt432.millennium.renderer.model.gltf.json.AccessorsItem;

/**
 * @author DustW
 **/
public class Accessor {
    GltfModel model;
    AccessorsItem json;

    public Accessor(GltfModel model, AccessorsItem json) {
        this.model = model;
        this.json = json;
    }

    public void init() {
        var bufferView = model.getBufferViews().get(json.getBufferView());

        ;
    }

    public enum Type {
        /** 标量 */
        SCALAR,
        /** 二维向量 */
        VEC2,
        /** 三维向量 */
        VEC3,
        /** 四维向量 */
        VEC4,
        /** 三维矩阵 */
        MAT3,
        /** 四维矩阵 */
        MAT4
    }
}
