package net.aurika.auspice.platform.biome;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface Biome extends Keyed, Translatable {

  @Override
  @NotNull Key key();

  @Override
  @NotNull String translationKey();

}
