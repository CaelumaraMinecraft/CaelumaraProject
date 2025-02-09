package net.aurika.data.api.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface SectionCreatableDataSetter extends DataSetter {
    @NotNull SectionableDataSetter createSection();
}
