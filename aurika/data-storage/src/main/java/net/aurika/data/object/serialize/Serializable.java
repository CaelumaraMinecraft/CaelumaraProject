package net.aurika.data.object.serialize;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.dataprovider.SectionableDataSetter;

public interface Serializable {
    void serialize(@NotNull SectionableDataSetter dataSetter);
}
