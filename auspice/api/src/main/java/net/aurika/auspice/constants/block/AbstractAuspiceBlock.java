package net.aurika.auspice.constants.block;

import net.aurika.auspice.platform.block.Block;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class AbstractAuspiceBlock implements AuspiceBlock {

  public AbstractAuspiceBlock(@NotNull AuspiceBlockType type, @NotNull Block platformBlock) {
    Validate.Arg.notNull(type, "type");
    Validate.Arg.notNull(platformBlock, "platformBlock");
    this.type = type;
    this.platformBlock = platformBlock;
  }

  private final @NotNull AuspiceBlockType type;
  protected @NotNull Block platformBlock;

  @Override
  public @NotNull AuspiceBlockType auspiceBlockType() {
    return type;
  }

}
