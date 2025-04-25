package net.aurika.auspice.platform.event.block;

import net.aurika.auspice.platform.event.player.PlayerEvent;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

import static net.aurika.auspice.platform.event.block.BlockBreakEvent$Companion.EMITTER_CONTAINER;

@Listenable
public interface BlockBreakByPlayerEvent extends BlockEvent, PlayerEvent {

  static @NotNull Emitter<BlockBreakByPlayerEvent> emitter() {
    return EMITTER_CONTAINER.emitter();
  }

  @EmitterReplaceMethod
  static @NotNull Emitter<BlockBreakByPlayerEvent> replaceEmitter(@NotNull Emitter<BlockBreakByPlayerEvent> newEmitter) {
    return EMITTER_CONTAINER.replaceEmitter(newEmitter);
  }

  @Override
  default @NotNull Emitter<? extends BlockBreakByPlayerEvent> eventEmitter() { return emitter(); }

}

final class BlockBreakEvent$Companion {

  static final EmitterContainer<BlockBreakByPlayerEvent> EMITTER_CONTAINER = new EmitterContainer<>(
      BlockBreakByPlayerEvent.class);

}
