package tt432.millennium.auto;

import icyllis.modernui.fragment.Fragment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegUILogic {
    private static final Map<Class<?>, Function<AbstractContainerMenu, Fragment>> FACTORY = new HashMap<>();

    public static boolean isUIMenu(AbstractContainerMenu menu) {
        return FACTORY.containsKey(menu.getClass());
    }

    public static Fragment create(AbstractContainerMenu menu) {
        return FACTORY.get(menu.getClass()).apply(menu);
    }

    public static void register() {
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        Type type = Type.getType(RegUI.class);

        for (var scanData : allScanData) {
            Set<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();

            for (var annotation : annotations) {
                if (Objects.equals(annotation.annotationType(), type)) {
                    try {
                        Class<?> aClass = Class.forName(annotation.memberName());
                        Class<?> fClass = Class.forName((String) annotation.annotationData().get("value"));

                        if (!Fragment.class.isAssignableFrom(fClass)) {
                            throw new ClassCastException(fClass.getName() + " can't cast to Fragment");
                        }

                        for (Constructor<?> constructor : fClass.getConstructors()) {
                            Class<?>[] parameterTypes = constructor.getParameterTypes();

                            if (parameterTypes.length != 1) {
                                continue;
                            }

                            if (AbstractContainerMenu.class.isAssignableFrom(parameterTypes[0])) {
                                FACTORY.put(aClass, m -> {
                                    try {
                                        return (Fragment) constructor.newInstance(m);
                                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                });

                                break;
                            }

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
