package top.auspice.data.object.serialize;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SectionableDataSetter;

public interface Serializable {
    void serialize(@NotNull SectionableDataSetter dataSetter);
}
