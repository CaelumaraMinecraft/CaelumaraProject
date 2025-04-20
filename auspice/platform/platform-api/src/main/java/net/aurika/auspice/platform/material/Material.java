package net.aurika.auspice.platform.material;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

/**
 * A material.
 * 材料覆盖了方块类型和物品类型.
 */
public interface Material extends Keyed, Translatable {

  @Override
  @NotNull Key key();

  @Override
  @NotNull String translationKey();

  int defaultMaxStackSize();

}
