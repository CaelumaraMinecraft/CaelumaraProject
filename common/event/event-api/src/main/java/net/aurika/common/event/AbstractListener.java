package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractListener<E extends Event> implements Listener<E> {

  private final @NotNull Ident id;
  private final @NotNull Class<? extends E> listenedEventType;

  public AbstractListener(
      @NotNull Ident id,
      @NotNull Class<? extends E> listenedEventType
  ) {
    Validate.Arg.notNull(id, "id");
    Validate.Arg.notNull(listenedEventType, "listenedEventType");
    this.id = id;
    this.listenedEventType = listenedEventType;
  }

  @Override
  public @NotNull Ident ident() { return id; }

  @Override
  public abstract void accept(@NotNull E event);

  @Override
  public @NotNull Class<? extends E> listenedEventType() { return listenedEventType; }

}
