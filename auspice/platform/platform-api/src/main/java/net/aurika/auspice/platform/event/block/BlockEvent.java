package net.aurika.auspice.platform.event.block;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.auspice.platform.block.Block;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a block relative event.
 */
@Listenable
public interface BlockEvent extends MinecraftEvent {

  static @NotNull Emitter<BlockEvent> emitter() { return BlockEvent$Companion.EMITTER_CONTAINER.emitter(); }

  @EmitterReplaceMethod
  static @NotNull Emitter<BlockEvent> replaceEmitter(@NotNull Emitter<BlockEvent> newEmitter) {
    synchronized (BlockEvent$Companion.EMITTER_CONTAINER) {
      return BlockEvent$Companion.EMITTER_CONTAINER.replaceEmitter(newEmitter);
    }
  }

  Block block();

  @Override
  default @NotNull Emitter<? extends BlockEvent> eventEmitter() { return emitter(); }

}

final class BlockEvent$Companion {

  static final EmitterContainer<BlockEvent> EMITTER_CONTAINER = new EmitterContainer<>(BlockEvent.class);

}
