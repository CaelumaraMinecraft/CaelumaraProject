package net.aurika.auspice.platform.art;

import net.aurika.auspice.platform.registry.TypedKey;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Art extends Keyed {

  @Override
  @NotNull TypedKey<? extends Art> key();

}
