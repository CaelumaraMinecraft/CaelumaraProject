package net.aurika.kingdoms.territories.constant.land.lease.project.type;

import org.jetbrains.annotations.NotNull;

public interface LandLeaseProjectTypeAware {

  /**
   * Gets the land lease type.
   *
   * @return the type
   */
  @NotNull LandLeaseProjectType<?> landLeaseProjectType();

}
