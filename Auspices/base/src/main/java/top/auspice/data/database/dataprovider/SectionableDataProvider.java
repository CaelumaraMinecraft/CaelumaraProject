package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface SectionableDataProvider {
    @NotNull
    SectionableDataProvider get(@NotNull String var1);
}
