package net.aurika.kingdoms.territories.constant.land.lease.project.type;

import net.aurika.kingdoms.territories.constant.land.lease.project.object.LandRules;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.abstraction.data.DeserializationContext;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;

import static net.aurika.kingdoms.territories.TerritoriesAddon.buildNS;

public class LandLeaseProjectTypeLandRules extends LandLeaseProjectType<LandRules> {

  public LandLeaseProjectTypeLandRules() {
    this(buildNS("LAND_RULES"));
  }

  protected LandLeaseProjectTypeLandRules(@NotNull Namespace namespace) {
    super(namespace);
  }

  @Override
  public @NotNull LandRules deserialize(@NotNull DeserializationContext<SectionableDataGetter> deserializationContext) {
    return new LandRules();
  }

}
