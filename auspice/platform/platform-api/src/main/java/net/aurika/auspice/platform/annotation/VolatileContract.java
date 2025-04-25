package net.aurika.auspice.platform.annotation;

import org.jetbrains.annotations.Contract;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface VolatileContract {

  Contract value();

}
