package net.aurika.common.examination;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExaminablePropertyGetter {

  String value();

}
