package top.auspice.constants.metadata;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SectionCreatableDataSetter;

public interface AuspiceMetadata {

    Object getValue();

    void setValue(Object value);

    void serialize(@NotNull KeyedAuspiceObject<?, ?> auspiceObject, @NotNull SectionCreatableDataSetter dataSetter);

    default boolean shouldSave(@NotNull KeyedAuspiceObject<?, ?> auspiceObject) {
        return true;
    }

}
