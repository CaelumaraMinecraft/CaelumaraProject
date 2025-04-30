package net.aurika.auspice.platform.material;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A material.
 * 材料覆盖了方块类型和物品类型.
 */
@ApiStatus.Experimental
public interface Material extends Keyed, Translatable {

  @Override
  @NotNull Key key();

  @Override
  @NotNull String translationKey();

  /**
   * 材料作为物品的默认最大堆叠数量
   *
   * @return the default max stack size
   */
  int defaultMaxStackSize();

}
