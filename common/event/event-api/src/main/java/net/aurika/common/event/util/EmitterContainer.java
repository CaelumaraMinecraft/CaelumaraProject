package net.aurika.common.event.util;

import net.aurika.common.event.Emitter;
import net.aurika.common.event.Event;
import net.aurika.common.event.EventAPI;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public final class EmitterContainer<E extends Event> {

  private final @NotNull Class<E> eventType;
  private @NotNull Emitter<E> emitter;

  public EmitterContainer(@NotNull Class<E> eventType) {
    Validate.Arg.notNull(eventType, "eventType");
    this.eventType = eventType;
    this.emitter = EventAPI.defaultEmitter(eventType);
  }

  public EmitterContainer(@NotNull Class<E> eventType, @NotNull Emitter<E> emitter) {
    Validate.Arg.notNull(eventType, "eventType");
    Validate.Arg.notNull(emitter, "emitter");
    this.eventType = eventType;
    this.emitter = emitter;
  }

  public @NotNull Class<E> eventType() {
    return this.eventType;
  }

  public @NotNull Emitter<E> emitter() {
    return this.emitter;
  }

  public @NotNull Emitter<E> replaceEmitter(@NotNull Emitter<E> newEmitter) {
    Validate.Arg.notNull(newEmitter, "newEmitter");
    if (newEmitter.eventType() != this.eventType) {
      throw new IllegalArgumentException("Cannot replace emitter of event type " + newEmitter.eventType());
    }
    Emitter<E> oleEmitter = this.emitter;
    this.emitter = newEmitter;
    return oleEmitter;
  }

}
