package net.aurika.xariaz.api.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface SectionDataProvider extends DataProvider, SectionDataGetter, SectionDataSetter {

  @Override
  @NotNull DataProvider get(@NotNull String name);

}
