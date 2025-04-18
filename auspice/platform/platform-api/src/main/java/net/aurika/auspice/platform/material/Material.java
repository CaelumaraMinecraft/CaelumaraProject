package net.aurika.auspice.platform.material;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface Material extends Keyed, Translatable {

  @Override
  @NotNull Key key();

  @Override
  @NotNull String translationKey();

}
