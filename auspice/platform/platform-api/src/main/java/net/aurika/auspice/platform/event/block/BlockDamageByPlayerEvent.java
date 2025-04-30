package net.aurika.auspice.platform.event.block;

import net.aurika.auspice.platform.event.player.PlayerEvent;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.ConduitReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

import static net.aurika.auspice.platform.event.block.BlockDamageByPlayerEvent$Companion.EMITTER_CONTAINER;

/**
 * Called when a player damages a block.
 * <p>If a Block Damage event is canceled, the block will not be damaged.</p>
 */
@Listenable
public interface BlockDamageByPlayerEvent extends BlockEvent, PlayerEvent {

  static @NotNull Conduit<BlockDamageByPlayerEvent> emitter() {
    return EMITTER_CONTAINER.conduit();
  }

  @ConduitReplaceMethod
  static @NotNull Conduit<BlockDamageByPlayerEvent> replaceEmitter(@NotNull Conduit<BlockDamageByPlayerEvent> newConduit) {
    return EMITTER_CONTAINER.replaceConduit(newConduit);
  }

  @Override
  default @NotNull Conduit<? extends BlockDamageByPlayerEvent> eventConduit() { return emitter(); }

}

final class BlockDamageByPlayerEvent$Companion {

  static final EmitterContainer<BlockDamageByPlayerEvent> EMITTER_CONTAINER = new EmitterContainer<>(
      BlockDamageByPlayerEvent.class);

}
