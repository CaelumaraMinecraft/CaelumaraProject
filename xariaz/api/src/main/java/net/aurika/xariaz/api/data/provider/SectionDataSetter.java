package net.aurika.xariaz.api.data.provider;

import org.jetbrains.annotations.NotNull;

public interface SectionDataSetter extends DataSetter {

  @NotNull DataSetter get(@NotNull String name);

}
