package top.auspice.api.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface Buildable {
    Class<?>[] fromBuilder();
}
