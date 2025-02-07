package net.aurika.data.object.serialize;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.dataprovider.SectionableDataGetter;

public interface Deserializable {
    void deserialize(@NotNull SectionableDataGetter dataGetter);
}
