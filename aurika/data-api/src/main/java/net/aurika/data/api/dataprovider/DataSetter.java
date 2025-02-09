package net.aurika.data.api.dataprovider;

import net.aurika.data.api.DataStringRepresentation;
import net.aurika.data.api.structure.SimpleData;
import net.aurika.data.api.structure.SimpleDataObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface DataSetter {
    default void setString(@NotNull DataStringRepresentation value) {
        Objects.requireNonNull(value, "value");
        this.setString(value.asDataString());
    }

    void setString(@NotNull String value);

    void setInt(int value);

    void setLong(long value);

    void setFloat(float value);

    void setDouble(double value);

    void setBoolean(boolean value);

    void setUUID(@NotNull UUID value);

    default void setObject(@NotNull SimpleDataObject value) {
        Objects.requireNonNull(value, "value");
        setObject(value.simpleData());
    }

    void setObject(@NotNull SimpleData value);

    <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> var2);

    <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2);
}
