package net.aurika.common.data.provider;

import org.jetbrains.annotations.NotNull;

public interface SectionableDataSetter extends DataSetter {

  @NotNull SectionableDataSetter get(@NotNull String name) throws IllegalDataStructException;

  @NotNull SectionableDataSetter createSection(@NotNull String name) throws IllegalDataStructException;

  @NotNull DynamicSection createDynamicSection(@NotNull String name) throws IllegalDataStructException;

}
