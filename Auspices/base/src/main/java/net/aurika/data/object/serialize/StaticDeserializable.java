package net.aurika.data.object.serialize;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.dataprovider.SectionableDataGetter;

public interface StaticDeserializable<T> {
    T deserialize(@NotNull SectionableDataGetter getter);
}
