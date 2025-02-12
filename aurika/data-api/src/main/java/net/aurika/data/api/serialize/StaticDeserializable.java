package net.aurika.data.api.serialize;

import net.aurika.data.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

public interface StaticDeserializable<T> {
    T deserialize(@NotNull SectionableDataGetter getter);
}
