package net.aurika.configuration.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {

  String BLANK = "";

  String[] value();

  boolean forParent() default false;

}