package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

public interface Listener<E extends Event> {

  void accept(@NotNull E event);

  @NotNull Class<? extends E> listenedEventType();

  boolean ignoreCancelled();

}
