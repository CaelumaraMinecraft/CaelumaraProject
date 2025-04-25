package net.aurika.auspice.platform;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a block, entity, or other object that may receive a custom name.
 */
public interface CustomNameable {

  /**
   * Gets the custom name.
   *
   * <p>This value has no effect on players, they will always use their real name.</p>
   *
   * @return the custom name
   */
  @Nullable Component customName();

  /**
   * Sets the custom name.
   *
   * <p>This name will be used in death messages and can be sent to the client as a nameplate over the mob.</p>
   *
   * <p>Setting the name to {@code null} will clear it.</p>
   *
   * <p>This value has no effect on players, they will always use their real name.</p>
   *
   * @param customName the custom name to set
   */
  void customName(@Nullable Component customName);

}
