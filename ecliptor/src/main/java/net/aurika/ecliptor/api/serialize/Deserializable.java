package net.aurika.ecliptor.api.serialize;

import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

public interface Deserializable {

  void deserialize(@NotNull SectionableDataGetter dataGetter);

}
