package net.aurika.common.event;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DelegateEmitter<E extends Event> implements Emitter<E> {

  private final @NotNull Class<E> eventType;
  private @NotNull Emitter<E> delegate;

  protected DelegateEmitter(@NotNull Emitter<E> delegate) {
    this(delegate.eventType(), delegate);
  }

  protected DelegateEmitter(@NotNull Class<E> eventType, @NotNull Emitter<E> delegate) {
    Validate.Arg.notNull(eventType, "eventType");
    Validate.Arg.notNull(delegate, "delegate");
    this.eventType = eventType;
    delegate(delegate);
  }

  public @NotNull Emitter<E> delegate() {
    return this.delegate;
  }

  public void delegate(@NotNull Emitter<E> delegate) {
    Validate.Arg.notNull(delegate, "delegate");
    if (delegate.eventType() != this.eventType) {
      throw new IllegalArgumentException("Delegate event type mismatch");
    }
    this.delegate = delegate;
  }

  @Override
  public void emit(@NotNull E event) {
    delegate.emit(event);
  }

  @Override
  public void register(@NotNull Listener<E> listener) {
    delegate.register(listener);
  }

  @Override
  public @NotNull Class<E> eventType() {
    return eventType;
  }

  @Override
  public @NotNull Emitter<? super E> @NotNull [] directParentEmitters() {
    return delegate.directParentEmitters();
  }

  @Override
  public @NotNull Set<@NotNull Emitter<? super E>> allParentEmitters() {
    return delegate.allParentEmitters();
  }

  @Override
  public @NotNull Listener<E> @NotNull [] listeners() {
    return delegate.listeners();
  }

}
