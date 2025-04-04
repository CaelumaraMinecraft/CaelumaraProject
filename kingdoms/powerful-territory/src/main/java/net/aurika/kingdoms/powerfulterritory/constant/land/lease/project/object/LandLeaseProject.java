package net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.object;

import net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.type.LandLeaseProjectType;
import net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.type.LandLeaseProjectTypeAware;
import org.jetbrains.annotations.NotNull;

public interface LandLeaseProject extends LandLeaseProjectTypeAware {

  @Override
  @NotNull LandLeaseProjectType landLeaseType();

}
