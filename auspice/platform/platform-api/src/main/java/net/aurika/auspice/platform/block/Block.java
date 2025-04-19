package net.aurika.auspice.platform.block;

import net.aurika.auspice.platform.block.data.BlockData;
import net.aurika.auspice.platform.block.data.BlockDataAware;
import net.aurika.auspice.platform.material.Material;
import net.aurika.auspice.platform.material.MaterialAware;
import org.jetbrains.annotations.NotNull;

public interface Block extends BlockTypeAware, BlockDataAware, MaterialAware {

  @Override
  @NotNull BlockType blockType();

  @Override
  @NotNull BlockData blockData();

  void blockData(@NotNull BlockData blockData)throws ClassCastException;

  @Override
  @NotNull Material material();

}
