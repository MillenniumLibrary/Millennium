package tt432.millennium.renderer.model.gltf.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import tt432.millennium.renderer.model.gltf.json.NodesItem;

/**
 * @author DustW
 **/
public class Node {
    NodesItem json;

    public Node(NodesItem json) {
        this.json = json;
    }

    public void transform(PoseStack poseStack) {
        poseStack.pushPose();

        var translation = json.getTranslation();
        var scale = json.getScale();
        var rotation = json.getRotation();

        poseStack.translate(translation.get(0), translation.get(1), translation.get(2));
        poseStack.scale(scale.get(0), scale.get(1), scale.get(2));
        poseStack.mulPose(new Quaternion(rotation.get(0), rotation.get(1), rotation.get(2), rotation.get(3)));

        // TODO: 变换

        poseStack.popPose();
    }
}
