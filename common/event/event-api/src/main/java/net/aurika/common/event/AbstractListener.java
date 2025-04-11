package net.aurika.common.event;

import net.aurika.common.key.Key;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractListener<E extends Event> implements Listener<E> {

  private final @NotNull Key key;
  private final @NotNull Class<? extends E> listenedEventType;
  private final boolean ignoreCancelled;
  private final @NotNull Transformer<? extends E> container;

  public AbstractListener(
      @NotNull Key key,
      @NotNull Transformer<? extends E> container,
      boolean ignoreCancelled,
      @NotNull Class<? extends E> listenedEventType
  ) {
    Validate.Arg.notNull(key, "key");
    Validate.Arg.notNull(container, "container");
    Validate.Arg.notNull(listenedEventType, "listenedEventType");
    this.key = key;
    this.container = container;
    this.ignoreCancelled = ignoreCancelled;
    this.listenedEventType = listenedEventType;
  }

  @Override
  public @NotNull Key key() { return key; }

  @Override
  public abstract void accept(@NotNull E event);

  @Override
  public @NotNull Transformer<? extends E> container() { return container; }

  @Override
  public boolean ignoreCancelled() { return ignoreCancelled; }

  @Override
  public @NotNull Class<? extends E> listenedEventType() { return listenedEventType; }

}
