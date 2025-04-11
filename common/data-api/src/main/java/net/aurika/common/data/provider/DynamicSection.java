package net.aurika.common.data.provider;

import org.jetbrains.annotations.NotNull;

public interface DynamicSection extends AutoCloseable {

  @NotNull SectionableDataSetter setter();

}
