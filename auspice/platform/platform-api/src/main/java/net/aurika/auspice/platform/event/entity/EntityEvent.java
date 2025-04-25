package net.aurika.auspice.platform.event.entity;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.auspice.platform.entity.abstraction.Entity;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface EntityEvent extends MinecraftEvent {

  static @NotNull Emitter<EntityEvent> emitter() { return EntityEvent$Companion.EMITTER_CONTAINER.emitter(); }

  /**
   * @see net.aurika.auspice.platform.event.player.PlayerEvent#replaceEmitter(Emitter)
   */
  @EmitterReplaceMethod
  static @NotNull Emitter<EntityEvent> replaceEmitter(@NotNull Emitter<EntityEvent> newEmitter) {
    synchronized (EntityEvent$Companion.EMITTER_CONTAINER) {
      return EntityEvent$Companion.EMITTER_CONTAINER.replaceEmitter(newEmitter);
    }
  }

  /**
   * Returns the entity involved in this event
   *
   * @return entity who is involved in this event
   */
  Entity entity();

}

final class EntityEvent$Companion {

  static final EmitterContainer<EntityEvent> EMITTER_CONTAINER = new EmitterContainer<>(EntityEvent.class);

}
