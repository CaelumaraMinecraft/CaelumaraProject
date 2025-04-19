package net.aurika.auspice.platform.block.data;

import net.aurika.auspice.platform.material.Material;
import net.aurika.auspice.platform.material.MaterialAware;
import org.jetbrains.annotations.NotNull;

public interface BlockData extends MaterialAware, BlockDataAware, Cloneable {

  @Override
  @NotNull Material material();

  @Override
  default @NotNull BlockData blockData() { return this; }

  @NotNull BlockData clone();

}
