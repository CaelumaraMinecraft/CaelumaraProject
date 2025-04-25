package net.aurika.auspice.platform.bukkit.api.event.block;

import net.aurika.auspice.platform.event.block.BlockBreakByPlayerEvent;
import net.aurika.common.event.Emitter;
import org.jetbrains.annotations.NotNull;

public interface AbstractBukkitBlockBreakByPlayerEvent extends AbstractBukkitBlockEvent, BlockBreakByPlayerEvent {

  @Override
  @NotNull Emitter<? extends AbstractBukkitBlockEvent> eventEmitter();

}
