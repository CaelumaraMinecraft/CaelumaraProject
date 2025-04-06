package net.aurika.common.event;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractListener<E extends Event> implements Listener<E> {

  private final @NotNull Class<? extends E> listenedEventType;
  private final boolean ignoreCancelled;

  public AbstractListener(@NotNull Class<? extends E> listenedEventType, boolean ignoreCancelled) {
    Validate.Arg.notNull(listenedEventType, "listenedEventType");
    this.listenedEventType = listenedEventType;
    this.ignoreCancelled = ignoreCancelled;
  }

  @Override
  public @NotNull Class<? extends E> listenedEventType() {
    return listenedEventType;
  }

  @Override
  public boolean ignoreCancelled() {
    return ignoreCancelled;
  }


}
