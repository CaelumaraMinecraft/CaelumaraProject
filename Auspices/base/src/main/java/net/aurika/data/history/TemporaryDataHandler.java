package net.aurika.data.history;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.dataprovider.SectionableDataGetter;

public interface TemporaryDataHandler<H extends DataHolder> {
    void load(@NotNull SectionableDataGetter provider, @NotNull H dataHolder);
}
