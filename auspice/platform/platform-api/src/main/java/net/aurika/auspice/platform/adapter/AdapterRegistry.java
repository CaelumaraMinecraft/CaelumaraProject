package net.aurika.auspice.platform.adapter;

import net.aurika.common.annotation.container.ThrowOnAbsent;
import org.jetbrains.annotations.NotNull;

public interface AdapterRegistry {

  /**
   * Gets the adapter that the auspice object type is {@code auspiceType}.
   *
   * @param auspiceType the auspice type
   * @param <AT>        the auspice type
   * @param <PT>        the actual platform object type
   * @return the adapter
   */
  @ThrowOnAbsent
  @NotNull <AT, PT> Adapter<AT, PT> getByAuspice(@NotNull Class<AT> auspiceType);

  /**
   * Gets the adapter that the platform object type is {@code platformType}.
   *
   * @param platformType the platform object  type
   * @param <AT>         the auspice type
   * @param <PT>         the actual platform object type
   * @return the adapter
   */
  @ThrowOnAbsent
  @NotNull <AT, PT> Adapter<AT, PT> getByPlatform(@NotNull Class<PT> platformType);

}
