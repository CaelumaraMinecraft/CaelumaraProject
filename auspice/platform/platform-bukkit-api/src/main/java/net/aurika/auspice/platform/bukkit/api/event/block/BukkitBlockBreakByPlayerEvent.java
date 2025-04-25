package net.aurika.auspice.platform.bukkit.api.event.block;

import net.aurika.auspice.platform.event.block.BlockBreakByPlayerEvent;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EventAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitBlockBreakByPlayerEvent extends org.bukkit.event.block.BlockBreakEvent implements AbstractBukkitBlockBreakByPlayerEvent {

  @Contract("-> new")
  protected static @NotNull Emitter<BlockBreakByPlayerEvent> emitter() {
    return EventAPI.defaultEmitter(BlockBreakByPlayerEvent.class);
  }

  public BukkitBlockBreakByPlayerEvent(org.bukkit.block.Block theBlock, Player player) {
    super(theBlock, player);
  }

  @Override
  public @Nullable org.bukkit.event.block.BlockBreakEvent toBukkitEvent() {
    return this;
  }

  @Override
  public @NotNull Emitter<? extends AbstractBukkitBlockEvent> eventEmitter() {
    return null;
  }

}
