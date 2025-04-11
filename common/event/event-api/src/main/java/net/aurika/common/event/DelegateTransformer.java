package net.aurika.common.event;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DelegateTransformer<E extends Event> implements Transformer<E> {

  private final @NotNull Class<E> eventType;
  private @NotNull Transformer<E> delegate;

  protected DelegateTransformer(@NotNull Transformer<E> delegate) {
    this(delegate.eventType(), delegate);
  }

  protected DelegateTransformer(@NotNull Class<E> eventType, @NotNull Transformer<E> delegate) {
    Validate.Arg.notNull(eventType, "eventType");
    Validate.Arg.notNull(delegate, "delegate");
    this.eventType = eventType;
    delegate(delegate);
  }

  public @NotNull Transformer<E> delegate() {
    return this.delegate;
  }

  public void delegate(@NotNull Transformer<E> delegate) {
    Validate.Arg.notNull(delegate, "delegate");
    if (delegate.eventType() != this.eventType) {
      throw new IllegalArgumentException("Delegate event type mismatch");
    }
    this.delegate = delegate;
  }

  @Override
  public void transform(@NotNull E event) {
    delegate.transform(event);
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
  public @NotNull Transformer<? super E> @NotNull [] directParentTransformers() {
    return delegate.directParentTransformers();
  }

  @Override
  public @NotNull Set<@NotNull Transformer<? super E>> allParentTransformers() {
    return delegate.allParentTransformers();
  }

  @Override
  public @NotNull Listener<E> @NotNull [] listeners() {
    return delegate.listeners();
  }

}
