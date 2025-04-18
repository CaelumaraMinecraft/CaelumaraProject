package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractListener<E extends Event> implements Listener<E> {

  private final @NotNull Ident id;
  private final @NotNull Class<? extends E> listenedEventType;
  private final boolean ignoreCancelled;
  private final @NotNull Emitter<? extends E> container;

  public AbstractListener(
      @NotNull Ident id,
      @NotNull Emitter<? extends E> container,
      boolean ignoreCancelled,
      @NotNull Class<? extends E> listenedEventType
  ) {
    Validate.Arg.notNull(id, "id");
    Validate.Arg.notNull(container, "container");
    Validate.Arg.notNull(listenedEventType, "listenedEventType");
    this.id = id;
    this.container = container;
    this.ignoreCancelled = ignoreCancelled;
    this.listenedEventType = listenedEventType;
  }

  @Override
  public @NotNull Ident ident() { return id; }

  @Override
  public abstract void accept(@NotNull E event);

  @Override
  public @NotNull Emitter<? extends E> container() { return container; }

  @Override
  public boolean ignoreCancelled() { return ignoreCancelled; }

  @Override
  public @NotNull Class<? extends E> listenedEventType() { return listenedEventType; }

}
