package net.aurika.auspice.event.api;

import net.aurika.common.event.Conduit;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface MinecraftEvent extends net.aurika.common.event.Event {

  static @NotNull Conduit<MinecraftEvent> emitter() { return MinecraftEvent$Companion.EMITTER_CONTAINER.conduit(); }

  @EmitterReplaceMethod
  static @NotNull Conduit<MinecraftEvent> replaceEmitter(@NotNull Conduit<MinecraftEvent> newConduit) {
    synchronized (MinecraftEvent$Companion.EMITTER_CONTAINER) {
      return MinecraftEvent$Companion.EMITTER_CONTAINER.replaceConduit(newConduit);
    }
  }

  default @NotNull Conduit<? extends MinecraftEvent> eventConduit() { return emitter(); }

}

final class MinecraftEvent$Companion {

  static final EmitterContainer<MinecraftEvent> EMITTER_CONTAINER = new EmitterContainer<>(MinecraftEvent.class);

}
