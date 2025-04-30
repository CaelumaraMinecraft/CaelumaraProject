package net.aurika.common.keyed;

import net.aurika.common.metalang.flow.Instance;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

@ApiStatus.Experimental
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Identified {

  String by();

  In in();

  @interface In {

    Instance value();

  }

}
