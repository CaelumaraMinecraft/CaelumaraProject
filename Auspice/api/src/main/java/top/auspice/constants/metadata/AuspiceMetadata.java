package top.auspice.constants.metadata;

import net.aurika.ecliptor.database.dataprovider.SectionCreatableDataSetter;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.base.KeyedAuspiceObject;

public interface AuspiceMetadata {

    Object getValue();

    void setValue(Object value);

    void serialize(@NotNull KeyedAuspiceObject<?> auspiceObject, @NotNull SectionCreatableDataSetter dataSetter);

    default boolean shouldSave(@NotNull KeyedAuspiceObject<?> auspiceObject) {
        return true;
    }
}
