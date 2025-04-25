package net.aurika.auspice.event.api;

import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface MinecraftEvent extends net.aurika.common.event.Event {

  static @NotNull Emitter<MinecraftEvent> emitter() { return MinecraftEvent$Companion.EMITTER_CONTAINER.emitter(); }

  @EmitterReplaceMethod
  static @NotNull Emitter<MinecraftEvent> replaceEmitter(@NotNull Emitter<MinecraftEvent> newEmitter) {
    synchronized (MinecraftEvent$Companion.EMITTER_CONTAINER) {
      return MinecraftEvent$Companion.EMITTER_CONTAINER.replaceEmitter(newEmitter);
    }
  }

  default @NotNull Emitter<? extends MinecraftEvent> eventEmitter() { return emitter(); }

}

final class MinecraftEvent$Companion {

  static final EmitterContainer<MinecraftEvent> EMITTER_CONTAINER = new EmitterContainer<>(MinecraftEvent.class);

}
