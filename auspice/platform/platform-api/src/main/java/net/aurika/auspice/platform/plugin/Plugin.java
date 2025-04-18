package net.aurika.auspice.platform.plugin;

import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.key.Namespaced;
import org.jetbrains.annotations.NotNull;

public interface Plugin extends Namespaced {

  /**
   * Gets the plugin namespace
   *
   * @return the plugin namespace
   */
  @Override
  @KeyPattern.Namespace
  @NotNull String namespace();

}
