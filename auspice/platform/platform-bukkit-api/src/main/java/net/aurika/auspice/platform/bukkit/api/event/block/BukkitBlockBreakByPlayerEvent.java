package net.aurika.auspice.platform.bukkit.api.event.block;

import net.aurika.auspice.platform.event.block.BlockBreakByPlayerEvent;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.EventAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see org.bukkit.event.block.BlockBreakEvent
 */
public class BukkitBlockBreakByPlayerEvent extends org.bukkit.event.block.BlockBreakEvent implements AbstractBukkitBlockBreakByPlayerEvent, BlockBreakByPlayerEvent {

  @Contract("-> new")
  protected static @NotNull Conduit<BukkitBlockBreakByPlayerEvent> conduit() {
    return EventAPI.defaultConduit(BukkitBlockBreakByPlayerEvent.class);
  }

  public BukkitBlockBreakByPlayerEvent(org.bukkit.block.Block theBlock, Player player) {
    super(theBlock, player);
  }

  @Override
  public @Nullable org.bukkit.event.block.BlockBreakEvent toBukkitEvent() {
    return this;
  }

  @Override
  public @NotNull Conduit<? extends BukkitBlockBreakByPlayerEvent> eventConduit() {
    return conduit();
  }

}
