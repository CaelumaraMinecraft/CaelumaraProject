package net.aurika.auspice.platform.event.world;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface WorldEvent extends MinecraftEvent {

  static @NotNull Conduit<? extends WorldEvent> emitter() { return WorldEvent$Companion.EMITTER_CONTAINER.conduit(); }

  /**
   * Change the emitter of the event.
   *
   * @param newConduit the new emitter
   * @return the old emitter
   */
  @EmitterReplaceMethod
  static @NotNull Conduit<? extends WorldEvent> replaceEmitter(@NotNull Conduit<WorldEvent> newConduit) {
    synchronized (WorldEvent$Companion.EMITTER_CONTAINER) {
      return WorldEvent$Companion.EMITTER_CONTAINER.replaceConduit(newConduit);
    }
  }

  /**
   * Gets the world primarily involved with this event
   *
   * @return world which caused this event
   */
  World world();

}

final class WorldEvent$Companion {

  static final EmitterContainer<WorldEvent> EMITTER_CONTAINER = new EmitterContainer<>(WorldEvent.class);

}
