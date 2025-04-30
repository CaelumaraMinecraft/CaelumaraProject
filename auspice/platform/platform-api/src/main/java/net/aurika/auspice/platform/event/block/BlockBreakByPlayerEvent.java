package net.aurika.auspice.platform.event.block;

import net.aurika.auspice.platform.event.player.PlayerEvent;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.ConduitReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

import static net.aurika.auspice.platform.event.block.BlockBreakEvent$Companion.CONDUIT_CONTAINER;

@Listenable
public interface BlockBreakByPlayerEvent extends BlockEvent, PlayerEvent {

  static @NotNull Conduit<BlockBreakByPlayerEvent> emitter() {
    return CONDUIT_CONTAINER.conduit();
  }

  @ConduitReplaceMethod
  static @NotNull Conduit<BlockBreakByPlayerEvent> replaceEmitter(@NotNull Conduit<BlockBreakByPlayerEvent> newConduit) {
    return CONDUIT_CONTAINER.replaceConduit(newConduit);
  }

  @Override
  default @NotNull Conduit<? extends BlockBreakByPlayerEvent> eventConduit() { return emitter(); }

}

final class BlockBreakEvent$Companion {

  static final EmitterContainer<BlockBreakByPlayerEvent> CONDUIT_CONTAINER = new EmitterContainer<>(
      BlockBreakByPlayerEvent.class);

}
