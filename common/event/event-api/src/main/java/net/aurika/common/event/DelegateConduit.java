package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class DelegateConduit<E extends Event> implements Conduit<E> {

  private final @NotNull Class<E> eventType;
  private @NotNull Conduit<E> delegate;

  protected DelegateConduit(@NotNull Conduit<E> delegate) {
    this(delegate.eventType(), delegate);
  }

  protected DelegateConduit(@NotNull Class<E> eventType, @NotNull Conduit<E> delegate) {
    Validate.Arg.notNull(eventType, "eventType");
    Validate.Arg.notNull(delegate, "delegate");
    this.eventType = eventType;
    delegate(delegate);
  }

  public @NotNull Conduit<E> delegate() {
    return this.delegate;
  }

  public void delegate(@NotNull Conduit<E> delegate) {
    Validate.Arg.notNull(delegate, "delegate");
    if (delegate.eventType() != this.eventType) {
      throw new IllegalArgumentException("Delegate event type mismatch");
    }
    this.delegate = delegate;
  }

  @Override
  public void transport(@NotNull E event) { delegate.transport(event); }

  @Override
  public void registerListener(@NotNull Listener<E> listener) { delegate.registerListener(listener); }

  @Override
  public @NotNull Class<E> eventType() { return eventType; }


  @Override
  public @NotNull Listener<E> @NotNull [] listeners() {
    return delegate.listeners();
  }

  @Override
  public @NotNull Listener<E> listenerAtIndex(int index) throws IndexOutOfBoundsException { return delegate.listenerAtIndex(index); }

  @Override
  public @NotNull Listener<E> listener(@NotNull Ident id) throws IllegalArgumentException { return delegate.listener(id); }

  @Override
  public int hashCode() { return super.hashCode(); }

  @Override
  public boolean equals(Object obj) { return super.equals(obj); }

  @Override
  public String toString() { return getClass().getSimpleName() + "(" + eventType + ")"; }

}
