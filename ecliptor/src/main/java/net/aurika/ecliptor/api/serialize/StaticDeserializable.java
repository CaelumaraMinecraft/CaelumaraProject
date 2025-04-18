package net.aurika.ecliptor.api.serialize;

import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

public interface StaticDeserializable<T> {

  T deserialize(@NotNull SectionableDataGetter getter);

}
