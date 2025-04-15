package net.aurika.xariaz.api.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface DataProvider extends DataGetter, DataSetter {

  @Override
  @NotNull SectionDataProvider asSection() throws DataNotSectionableException;

}
