package net.aurika.auspice.platform.event.player;

import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.platform.event.entity.EntityEvent;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface PlayerEvent extends EntityEvent {

  static @NotNull Conduit<PlayerEvent> emitter() { return PlayerEvent$Companion.EMITTER_CONTAINER.conduit(); }

  /**
   * Replaces the emitter of the event.
   *
   * @param newConduit the new emitter
   * @return the old emitter
   */
  @EmitterReplaceMethod
  static @NotNull Conduit<PlayerEvent> replaceEmitter(@NotNull Conduit<PlayerEvent> newConduit) {
    synchronized (PlayerEvent$Companion.EMITTER_CONTAINER) {
      return PlayerEvent$Companion.EMITTER_CONTAINER.replaceConduit(newConduit);
    }
  }

  /**
   * Returns the player involved in this event
   *
   * @return player who is involved in this event
   */
  @Override
  Player entity();

}

final class PlayerEvent$Companion {

  static final EmitterContainer<PlayerEvent> EMITTER_CONTAINER = new EmitterContainer<>(PlayerEvent.class);

}
