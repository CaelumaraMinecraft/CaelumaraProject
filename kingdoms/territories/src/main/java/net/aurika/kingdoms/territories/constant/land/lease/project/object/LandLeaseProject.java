package net.aurika.kingdoms.territories.constant.land.lease.project.object;

import net.aurika.kingdoms.territories.constant.land.lease.project.type.LandLeaseProjectType;
import net.aurika.kingdoms.territories.constant.land.lease.project.type.LandLeaseProjectTypeAware;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.abstraction.data.SerializationContext;
import org.kingdoms.data.Serializable;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;

public interface LandLeaseProject extends LandLeaseProjectTypeAware, Serializable {

  /**
   * Handle extra things, such as event handlers.
   */
  void handleExtra();

  @Override
  void serialize(@NotNull SerializationContext<SectionableDataSetter> serializationContext);

  @Override
  @NotNull LandLeaseProjectType<?> landLeaseProjectType();

}
