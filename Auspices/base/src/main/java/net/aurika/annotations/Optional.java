package net.aurika.annotations;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.PARAMETER)
@Nullable
public @interface Optional {
}
