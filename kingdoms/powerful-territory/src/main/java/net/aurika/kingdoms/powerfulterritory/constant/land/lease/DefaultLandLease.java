package net.aurika.kingdoms.powerfulterritory.constant.land.lease;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.abstraction.data.DeserializationContext;
import org.kingdoms.constants.land.abstraction.data.SerializationContext;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;

public class DefaultLandLease implements LandLease {

  private long startTime = System.currentTimeMillis();

  public DefaultLandLease() { }

  public DefaultLandLease(long startTime) {
    this.startTime = startTime;
  }

  public long startTime() {
    return startTime;
  }

  public void startTime(long startTime) {
    this.startTime = startTime;
  }

  @Override
  @MustBeInvokedByOverriders
  public void serialize(@NotNull SerializationContext<SectionableDataSetter> serializationContext) {
    Validate.Arg.notNull(serializationContext, "serializationContext");
    SectionableDataSetter dataSetter = serializationContext.getDataProvider();
    dataSetter.setLong("startTime", startTime);
  }

  @Override
  @MustBeInvokedByOverriders
  public void deserialize(@NotNull DeserializationContext<SectionableDataGetter> deserializationContext) {
    Validate.Arg.notNull(deserializationContext, "deserializationContext");
    SectionableDataGetter dataGetter = deserializationContext.getDataProvider();
    startTime = dataGetter.getLong("startTime");
  }

}
