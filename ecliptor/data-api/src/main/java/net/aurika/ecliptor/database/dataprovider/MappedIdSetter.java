package net.aurika.ecliptor.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface MappedIdSetter extends DataSetter {
    @NotNull SectionCreatableDataSetter getValueProvider();
}