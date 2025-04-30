package net.aurika.auspice.platform.event.block;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.auspice.platform.block.Block;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.ConduitReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a block relative event.
 */
@Listenable
public interface BlockEvent extends MinecraftEvent {

  static @NotNull Conduit<BlockEvent> emitter() { return BlockEvent$Companion.EMITTER_CONTAINER.conduit(); }

  @ConduitReplaceMethod
  static @NotNull Conduit<BlockEvent> replaceEmitter(@NotNull Conduit<BlockEvent> newConduit) {
    synchronized (BlockEvent$Companion.EMITTER_CONTAINER) {
      return BlockEvent$Companion.EMITTER_CONTAINER.replaceConduit(newConduit);
    }
  }

  Block block();

  @Override
  default @NotNull Conduit<? extends BlockEvent> eventConduit() { return emitter(); }

}

final class BlockEvent$Companion {

  static final EmitterContainer<BlockEvent> EMITTER_CONTAINER = new EmitterContainer<>(BlockEvent.class);

}
