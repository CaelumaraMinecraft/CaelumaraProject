package net.aurika.xariaz.api.data.provider;

import org.jetbrains.annotations.NotNull;

public interface DataProvider extends DataGetter, DataSetter {

  @Override
  @NotNull SectionDataProvider asSection() throws DataNotSectionableException;

}
