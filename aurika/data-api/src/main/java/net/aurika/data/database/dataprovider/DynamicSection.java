package net.aurika.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface DynamicSection extends AutoCloseable {
    @NotNull SectionableDataSetter getSetter();
}
