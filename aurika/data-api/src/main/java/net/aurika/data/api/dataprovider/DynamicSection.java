package net.aurika.data.api.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface DynamicSection extends AutoCloseable {
    @NotNull SectionableDataSetter getSetter();
}
