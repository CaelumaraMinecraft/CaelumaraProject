package net.aurika.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface Buildable {
    Class<?>[] fromBuilder();
}
