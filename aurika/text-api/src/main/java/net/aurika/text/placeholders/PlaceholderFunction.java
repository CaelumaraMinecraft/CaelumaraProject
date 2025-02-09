package net.aurika.text.placeholders;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PlaceholderFunction {
}
