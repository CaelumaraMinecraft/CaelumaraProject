package net.aurika.auspice.platform.bukkit.api.event.block;

import net.aurika.auspice.platform.event.block.BlockBreakByPlayerEvent;
import net.aurika.common.event.Conduit;
import org.jetbrains.annotations.NotNull;

public interface AbstractBukkitBlockBreakByPlayerEvent extends AbstractBukkitBlockEvent, BlockBreakByPlayerEvent {

  @Override
  @NotNull Conduit<? extends AbstractBukkitBlockEvent> eventConduit();

}
