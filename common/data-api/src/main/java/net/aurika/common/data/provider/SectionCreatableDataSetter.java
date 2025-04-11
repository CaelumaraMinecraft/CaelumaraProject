package net.aurika.common.data.provider;

import org.jetbrains.annotations.NotNull;

/**
 * The {@linkplain DataSetter} can create a {@linkplain SectionableDataSetter}.
 */
public interface SectionCreatableDataSetter extends DataSetter {

  @NotNull SectionableDataSetter createSection();

}
