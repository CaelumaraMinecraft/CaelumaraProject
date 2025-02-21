package net.aurika.ecliptor.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface DynamicSection extends AutoCloseable {
    @NotNull SectionableDataSetter setter();
}
