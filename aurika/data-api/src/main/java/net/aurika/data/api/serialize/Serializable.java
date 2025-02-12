package net.aurika.data.api.serialize;

import net.aurika.data.database.dataprovider.SectionableDataSetter;
import org.jetbrains.annotations.NotNull;

public interface Serializable {
    void serialize(@NotNull SectionableDataSetter dataSetter);
}
