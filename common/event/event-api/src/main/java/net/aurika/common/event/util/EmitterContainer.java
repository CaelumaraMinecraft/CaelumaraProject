package net.aurika.common.event.util;

import net.aurika.common.event.Conduit;
import net.aurika.common.event.Event;
import net.aurika.common.event.EventAPI;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public final class EmitterContainer<E extends Event> {

  private final @NotNull Class<E> eventType;
  private @NotNull Conduit<E> conduit;

  public EmitterContainer(@NotNull Class<E> eventType) {
    Validate.Arg.notNull(eventType, "eventType");
    this.eventType = eventType;
    this.conduit = EventAPI.defaultConduit(eventType);
  }

  public EmitterContainer(@NotNull Class<E> eventType, @NotNull Conduit<E> conduit) {
    Validate.Arg.notNull(eventType, "eventType");
    Validate.Arg.notNull(conduit, "emitter");
    this.eventType = eventType;
    this.conduit = conduit;
  }

  public @NotNull Class<E> eventType() {
    return this.eventType;
  }

  public @NotNull Conduit<E> conduit() {
    return this.conduit;
  }

  public @NotNull Conduit<E> replaceConduit(@NotNull Conduit<E> newConduit) {
    Validate.Arg.notNull(newConduit, "newConduit");
    if (newConduit.eventType() != this.eventType) {
      throw new IllegalArgumentException("Requires event type " + eventType + " but got " + newConduit.eventType());
    }
    Conduit<E> oleConduit = this.conduit;
    this.conduit = newConduit;
    return oleConduit;
  }

}
