package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.DataStringRepresentation;
import top.auspice.server.location.Location;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface DataSetter {
    void setString(@Nullable String var1);

    default void setString(@Nullable DataStringRepresentation var1) {
        this.setString(var1 != null ? var1.asDataString() : null);
    }

    void setInt(int var1);

    void setLocation(@Nullable Location var1);

    void setSimpleLocation(@Nullable SimpleBlockLocation var1);

    void setSimpleChunkLocation(@NotNull SimpleChunkLocation var1);

    void setLong(long var1);

    void setFloat(float var1);

    void setDouble(double var1);

    void setBoolean(boolean var1);

    void setUUID(@Nullable UUID var1);

    <V> void setCollection(@NotNull Collection<? extends V> var1, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2);

    <K, V> void setMap(@NotNull Map<K, ? extends V> var1, @NotNull MappingSetterHandler<K, V> var2);
}
