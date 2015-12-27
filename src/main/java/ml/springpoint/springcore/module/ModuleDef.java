package ml.springpoint.springcore.module;

import java.lang.annotation.*;

/**
 * Annotation that defines properties of a module, such as its name
 * and dependencies.
 *
 * @author SirFaizdat
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleDef {

    /**
     * The name of this module. This should be user friendly.
     */
    String name();

}