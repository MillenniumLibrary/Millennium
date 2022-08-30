package tt432.millennium.util.math.curve;

import lombok.AllArgsConstructor;
import tt432.millennium.util.math.Vector3;

/**
 * @author DustW
 */
@AllArgsConstructor
public class CircleCurve extends Curve {
    public final double r;
    public final Vector3 axis;
    public final Vector3 center;

    @Override
    public Vector3 getPoint(float t) {
        float v = (float) Math.toRadians(t * 360);
        return new Vector3(r, 0, 0).rotate(v, axis).add(center);
    }
}
