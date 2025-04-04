package net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.object;

import net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.type.LandLeaseProjectType;
import net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.type.LandLeaseProjectTypeLandRules;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.abstraction.data.SerializationContext;
import org.kingdoms.constants.land.structures.objects.Regulator;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;

public class LandRules implements LandLeaseProject, LandLeaseProjectLand {

  public static final LandLeaseProjectTypeLandRules TYPE = new LandLeaseProjectTypeLandRules();

  private Regulator.Rule rule;

  @Override
  public void handleExtra() {
  }

  @Override
  public void serialize(@NotNull SerializationContext<SectionableDataSetter> serializationContext) {
    SectionableDataSetter dataSetter = serializationContext.getDataProvider();
    dataSetter.setString("rule", rule.name());
  }

  @Override
  public @NotNull LandLeaseProjectType<? extends LandRules> landLeaseProjectType() {
    return TYPE;
  }

}
