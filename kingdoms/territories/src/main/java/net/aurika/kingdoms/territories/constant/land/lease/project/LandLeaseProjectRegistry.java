package net.aurika.kingdoms.territories.constant.land.lease.project;

import net.aurika.kingdoms.territories.constant.land.lease.project.object.LandRules;
import net.aurika.kingdoms.territories.constant.land.lease.project.type.LandLeaseProjectType;
import org.kingdoms.constants.namespace.NamespacedRegistry;

public class LandLeaseProjectRegistry extends NamespacedRegistry<LandLeaseProjectType<?>> {

  public LandLeaseProjectRegistry() {
    super();
  }

  public void registerDefaults() {
    register(LandRules.TYPE);
  }

}
