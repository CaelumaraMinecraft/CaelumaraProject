package net.aurika.config.part.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
public @interface NotNamed {
}
