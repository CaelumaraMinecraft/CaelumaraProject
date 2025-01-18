package top.auspice.constants.metadata;

import org.jetbrains.annotations.NotNull;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.database.dataprovider.SectionCreatableDataSetter;

import java.util.Objects;

public final class StandardAuspiceMetadata implements AuspiceMetadata {
    private Object value;

    public StandardAuspiceMetadata(@NotNull Object value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public void serialize(@NotNull KeyedAuspiceObject<?> auspiceObject, @NotNull SectionCreatableDataSetter dataSetter) {

    }
}
