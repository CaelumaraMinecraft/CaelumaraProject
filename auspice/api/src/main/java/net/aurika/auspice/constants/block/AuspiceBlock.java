package net.aurika.auspice.constants.block;

import net.aurika.auspice.platform.block.Block;
import org.jetbrains.annotations.NotNull;

public interface AuspiceBlock extends Block {

  @NotNull AuspiceBlockType auspiceBlockType();

}
