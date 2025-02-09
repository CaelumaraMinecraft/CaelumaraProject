package net.aurika.text.placeholders;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface PlaceholderParameter {
    @NotNull
    String name();
}