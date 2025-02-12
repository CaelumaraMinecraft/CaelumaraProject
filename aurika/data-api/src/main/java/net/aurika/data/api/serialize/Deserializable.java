package net.aurika.data.api.serialize;

import net.aurika.data.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

public interface Deserializable {
    void deserialize(@NotNull SectionableDataGetter dataGetter);
}
