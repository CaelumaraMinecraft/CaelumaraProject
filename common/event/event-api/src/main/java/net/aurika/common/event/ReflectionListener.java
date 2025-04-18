package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionListener<E extends Event> extends AbstractListener<E> {

  private final @NotNull Method method;
  private final Object instance;

  public ReflectionListener(
      @NotNull Ident ident,
      @NotNull Method method,
      Object instance,
      @NotNull Emitter<? extends E> container,
      boolean ignoreCancelled,
      @NotNull Class<? extends E> listenedEventType
  ) {
    super(ident, container, ignoreCancelled, listenedEventType);
    Validate.Arg.notNull(method, "method");
    this.method = method;
    this.instance = instance;
  }

  @Override
  public void accept(@NotNull E event) {
    Validate.Arg.notNull(event, "event");
    method.setAccessible(true);
    try {
      method.invoke(instance, event);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);  // TODO
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);  // TODO
    }
  }

}
