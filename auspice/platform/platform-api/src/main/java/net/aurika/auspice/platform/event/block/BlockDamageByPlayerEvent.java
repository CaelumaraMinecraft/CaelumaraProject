package net.aurika.auspice.platform.event.block;

import net.aurika.auspice.platform.event.player.PlayerEvent;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
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

  static @NotNull Emitter<BlockDamageByPlayerEvent> emitter() {
    return EMITTER_CONTAINER.emitter();
  }

  @EmitterReplaceMethod
  static @NotNull Emitter<BlockDamageByPlayerEvent> replaceEmitter(@NotNull Emitter<BlockDamageByPlayerEvent> newEmitter) {
    return EMITTER_CONTAINER.replaceEmitter(newEmitter);
  }

  @Override
  default @NotNull Emitter<? extends BlockDamageByPlayerEvent> eventEmitter() { return emitter(); }

}

final class BlockDamageByPlayerEvent$Companion {

  static final EmitterContainer<BlockDamageByPlayerEvent> EMITTER_CONTAINER = new EmitterContainer<>(
      BlockDamageByPlayerEvent.class);

}
