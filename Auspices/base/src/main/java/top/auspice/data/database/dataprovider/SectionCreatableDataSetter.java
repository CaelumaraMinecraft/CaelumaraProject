package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface SectionCreatableDataSetter extends DataSetter {
    @NotNull SectionableDataSetter createSection();
}
