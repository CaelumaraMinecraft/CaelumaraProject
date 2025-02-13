package net.aurika.ecliptor.database.dataprovider;

import org.jetbrains.annotations.NotNull;

/**
 * 需要先调用 {@linkplain #createSection()} 方法再进行数据存储
 */
public interface SectionCreatableDataSetter extends DataSetter {
    @NotNull SectionableDataSetter createSection();
}
