package tt432.millennium.recipes.base;


import net.minecraft.world.item.crafting.RecipeManager;
import tt432.millennium.recipes.impls.TemplateRecipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DustW
 *
 * 标注配方类，以便自动注册 BaseSerializer
 * @see RecipeManager (自动注册)
 * @see TemplateRecipe (使用范例)
 * @see BaseSerializer (通用解析器)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Recipe {
    String value();
}
