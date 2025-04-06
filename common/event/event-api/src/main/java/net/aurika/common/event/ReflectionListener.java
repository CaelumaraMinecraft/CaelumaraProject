package net.aurika.common.event;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

public class ReflectionListener<E extends Event> extends AbstractListener<E> {

  private final @NotNull Method method;

  public ReflectionListener(
      @NotNull Method method,
      @NotNull Class<? extends E> listenedEventType,
      boolean ignoreCancelled
  ) {
    super(listenedEventType, ignoreCancelled);
    Validate.Arg.notNull(method, "method");
    this.method = method;
  }

  @Override
  public void accept(@NotNull E event) {
    Validate.Arg.notNull(event, "event");
    method.setAccessible(true);
    method.invoke()
  }

}
