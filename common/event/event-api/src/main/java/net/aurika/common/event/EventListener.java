package net.aurika.common.event;

import net.aurika.common.key.KeyPatterns;

import java.lang.annotation.*;

/**
 * Annotates a method to reflect to a {@link ReflectionListener} and register to the listener container.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventListener {

  @KeyPatterns.Key
  String key();

  boolean ignoreCancelled() default false;

}
