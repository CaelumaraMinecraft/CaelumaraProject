package net.aurika.data.api.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface MappedIdSetter extends DataSetter {
    @NotNull SectionCreatableDataSetter getValueProvider();
}