package net.aurika.auspice.configs.messages.placeholders;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface PhParam {
    @NotNull String name();

    boolean optional() default false;
}