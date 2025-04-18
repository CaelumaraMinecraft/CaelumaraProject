package net.aurika.auspice.platform;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PACKAGE})
public @interface ImplementedGameEdition {

  GameEdition[] value();

}
