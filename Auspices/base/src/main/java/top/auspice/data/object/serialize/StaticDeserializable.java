package top.auspice.data.object.serialize;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SectionableDataGetter;

public interface StaticDeserializable<T> {
    T deserialize(@NotNull SectionableDataGetter getter);
}
