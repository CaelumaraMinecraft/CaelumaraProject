package net.aurika.ecliptor.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface SectionableDataProvider {

  @NotNull SectionableDataProvider get(@NotNull String key);

}
