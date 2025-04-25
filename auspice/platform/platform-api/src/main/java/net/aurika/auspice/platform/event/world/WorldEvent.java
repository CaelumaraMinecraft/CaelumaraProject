package net.aurika.auspice.platform.event.world;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface WorldEvent extends MinecraftEvent {

  static @NotNull Emitter<? extends WorldEvent> emitter() { return WorldEvent$Companion.EMITTER_CONTAINER.emitter(); }

  /**
   * Change the emitter of the event.
   *
   * @param newEmitter the new emitter
   * @return the old emitter
   */
  @EmitterReplaceMethod
  static @NotNull Emitter<? extends WorldEvent> replaceEmitter(@NotNull Emitter<WorldEvent> newEmitter) {
    synchronized (WorldEvent$Companion.EMITTER_CONTAINER) {
      return WorldEvent$Companion.EMITTER_CONTAINER.replaceEmitter(newEmitter);
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
