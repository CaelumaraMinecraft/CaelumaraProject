package net.aurika.auspice.platform.enchantment;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Enchantment extends Keyed {

  @Override
  @NotNull Key key();

}
