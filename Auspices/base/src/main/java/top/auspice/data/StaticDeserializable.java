package top.auspice.data;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SectionableDataGetter;

public interface StaticDeserializable<T> {
    T deserialize(@NotNull SectionableDataGetter getter);
}
