package net.aurika.auspice.platform.event.entity;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.auspice.platform.entity.abstraction.Entity;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface EntityEvent extends MinecraftEvent {

  static @NotNull Conduit<EntityEvent> emitter() { return EntityEvent$Companion.EMITTER_CONTAINER.conduit(); }

  /**
   * @see net.aurika.auspice.platform.event.player.PlayerEvent#replaceEmitter(Conduit)
   */
  @EmitterReplaceMethod
  static @NotNull Conduit<EntityEvent> replaceEmitter(@NotNull Conduit<EntityEvent> newConduit) {
    synchronized (EntityEvent$Companion.EMITTER_CONTAINER) {
      return EntityEvent$Companion.EMITTER_CONTAINER.replaceConduit(newConduit);
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
