package top.auspice.data.history;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SectionableDataGetter;

public interface TemporaryDataHandler<H extends DataHolder> {
    void load(@NotNull SectionableDataGetter provider, @NotNull H dataHolder);
}
