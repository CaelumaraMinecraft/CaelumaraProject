package net.aurika.auspice.platform.inventory;

import net.aurika.auspice.platform.block.Block;
import net.aurika.auspice.platform.block.BlockAware;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a block inventory holder - either a BlockState, or a regular
 * Block.
 */
public interface BlockInventoryHolder extends InventoryAware, BlockAware {

  @Override
  @NotNull Inventory inventory();

  /**
   * Gets the block associated with this holder.
   *
   * @return the block associated with this holder
   * @throws IllegalStateException if the holder is a block state and is not placed
   */
  @NotNull Block block();

}
