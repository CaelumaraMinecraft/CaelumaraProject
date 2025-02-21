package net.aurika.common.annotations;

import java.lang.annotation.*;

@Deprecated
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface Buildable {
    Class<?>[] fromBuilder();
}
