package net.aurika.ecliptor.history;

import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

public interface TemporaryDataHandler<H extends DataHolder> {

  void load(@NotNull SectionableDataGetter provider, @NotNull H dataHolder);

}
