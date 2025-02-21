package net.aurika.auspice.constants.metadata;

import net.aurika.ecliptor.database.dataprovider.SectionCreatableDataSetter;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.constants.base.KeyedAuspiceObject;

import java.util.Objects;

@Deprecated
public final class StandardAuspiceMetadata implements AuspiceMetadata {
    private Object value;

    public StandardAuspiceMetadata(@NotNull Object value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public Object value() {
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
