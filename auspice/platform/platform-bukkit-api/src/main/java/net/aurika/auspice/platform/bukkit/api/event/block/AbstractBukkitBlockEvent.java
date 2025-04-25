package net.aurika.auspice.platform.bukkit.api.event.block;

import net.aurika.auspice.event.bukkit.BukkitEvent;
import net.aurika.auspice.event.bukkit.NativeBukkitEvent;
import net.aurika.auspice.platform.bukkit.block.BukkitBlock;
import net.aurika.auspice.platform.bukkit.block.BukkitBlockAdapter;
import net.aurika.auspice.platform.event.block.BlockEvent;
import net.aurika.common.event.Emitter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a block related event.
 */
@NativeBukkitEvent(org.bukkit.event.block.BlockEvent.class)
public interface AbstractBukkitBlockEvent extends BukkitEvent, BlockEvent {

  @Override
  default BukkitBlock block() {
    org.bukkit.event.Event bukkitEvent = this.toBukkitEvent();
    if (bukkitEvent instanceof org.bukkit.event.block.BlockEvent) {
      org.bukkit.event.block.BlockEvent bukkitBlockEvent = (org.bukkit.event.block.BlockEvent) bukkitEvent;
      return BukkitBlockAdapter.get().adaptToAuspice(bukkitBlockEvent.getBlock());
    }
    throw new UnsupportedOperationException();
  }

  @Override
  @NotNull Emitter<? extends AbstractBukkitBlockEvent> eventEmitter();

}
