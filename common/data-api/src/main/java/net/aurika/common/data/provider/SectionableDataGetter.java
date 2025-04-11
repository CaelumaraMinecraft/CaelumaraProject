package net.aurika.common.data.provider;

import org.jetbrains.annotations.NotNull;

public interface SectionableDataGetter extends DataGetter {

  @NotNull SectionableDataGetter get(@NotNull String name) throws IllegalDataStructException;

  @NotNull SectionableDataGetter asSection() throws IllegalDataStructException;

  @NotNull SectionableDataGetter asDynamicSection() throws IllegalDataStructException;

}
