package net.aurika.common.event;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * Annotates an {@link Event} can be received by a {@link Listener}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Listenable {

  @ApiStatus.Experimental
  String emitterFieldName() default "EMITTER";

}
