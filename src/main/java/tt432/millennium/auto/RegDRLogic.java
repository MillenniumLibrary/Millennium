package tt432.millennium.auto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.registries.DeferredRegister;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegDRLogic {
    public static void register(IEventBus bus) {
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        Type type = Type.getType(RegDR.class);

        for (var scanData : allScanData) {
            Set<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();

            for (var annotation : annotations) {
                if (Objects.equals(annotation.annotationType(), type)) {
                    try {
                        Class<?> aClass = Class.forName(annotation.memberName());

                        for (Field field : aClass.getFields()) {
                            Object o = field.get(null);

                            if (field.isAnnotationPresent(RegDR.class) && o instanceof DeferredRegister dr) {
                                dr.register(bus);
                            }
                        }
                    } catch (ClassNotFoundException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
