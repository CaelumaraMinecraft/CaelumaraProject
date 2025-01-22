package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface MappedIdSetter extends DataSetter {
    @NotNull SectionCreatableDataSetter getValueProvider();
}