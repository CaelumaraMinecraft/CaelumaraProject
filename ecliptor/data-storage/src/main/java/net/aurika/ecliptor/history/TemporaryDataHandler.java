package net.aurika.ecliptor.history;

import org.jetbrains.annotations.NotNull;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;

public interface TemporaryDataHandler<H extends DataHolder> {
    void load(@NotNull SectionableDataGetter provider, @NotNull H dataHolder);
}
