package net.aurika.data.database.dataprovider;

import net.aurika.data.object.DataStringRepresentation;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface DataSetter {
    void setString(@NotNull String value);

    default void setString(@NotNull DataStringRepresentation value) {
        Objects.requireNonNull(value, "value");
        this.setString(value.asDataString());
    }

    void setInt(int value);

    void setLong(long value);

    void setFloat(float value);

    void setDouble(double value);

    void setBoolean(boolean value);

    void setUUID(@NotNull UUID value);

    void setLocation(@NotNull SimpleLocation value);

    void setSimpleLocation(@NotNull SimpleBlockLocation value);

    void setSimpleChunkLocation(@NotNull SimpleChunkLocation value);

    <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> var2);

    <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2);
}
