package net.aurika.xariaz.api.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface SectionDataSetter extends DataSetter {

  @NotNull DataSetter get(@NotNull String name);

}
