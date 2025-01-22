package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.data.DataStringRepresentation;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface DataSetter {
    void setString(@Nullable String s);

    default void setString(@Nullable DataStringRepresentation dataStringAble) {
        this.setString(dataStringAble != null ? dataStringAble.asDataString() : null);
    }

    void setInt(int var1);

    void setLocation(@Nullable SimpleLocation location);

    void setSimpleLocation(@Nullable SimpleBlockLocation blockLocation);

    void setSimpleChunkLocation(@Nullable SimpleChunkLocation chunkLocation);

    void setLong(long l);

    void setFloat(float f);

    void setDouble(double d);

    void setBoolean(boolean b);

    void setUUID(@Nullable UUID uuid);

    <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2);

    <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> var2);
}
