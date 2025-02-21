package net.aurika.ecliptor.api.serialize;

import net.aurika.ecliptor.database.dataprovider.SectionableDataSetter;
import org.jetbrains.annotations.NotNull;

public interface Serializable {
    void serialize(@NotNull SectionableDataSetter dataSetter);
}
