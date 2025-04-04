package net.aurika.kingdoms.powerfulterritory.constant.land.lease;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.abstraction.data.DeserializationContext;
import org.kingdoms.constants.land.abstraction.data.SerializationContext;
import org.kingdoms.data.Deserializable;
import org.kingdoms.data.Serializable;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;

public interface LandLease extends Serializable, Deserializable {

  @Override
  void serialize(@NotNull SerializationContext<SectionableDataSetter> serializationContext);

  @Override
  void deserialize(@NotNull DeserializationContext<SectionableDataGetter> deserializationContext);

}
