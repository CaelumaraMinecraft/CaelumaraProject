package net.aurika.data.api.serialize;

import net.aurika.data.api.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

public interface Deserializable {
    void deserialize(@NotNull SectionableDataGetter dataGetter);
}
