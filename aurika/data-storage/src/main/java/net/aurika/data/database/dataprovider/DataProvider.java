package net.aurika.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface DataProvider extends SectionCreatableDataSetter, SectionableDataGetter, SectionableDataSetter {
    @NotNull DataProvider get(@NotNull String key);
}
