package net.aurika.auspice.platform.block.banner;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface PatternType extends Keyed {

  @Override
  @NotNull Key key();

}
