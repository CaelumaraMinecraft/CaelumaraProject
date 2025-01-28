package net.aurika.data.database.compressor;

import net.aurika.data.compressor.DataCompressor;
import net.aurika.data.database.dataprovider.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public final class CompressorDataProvider implements SectionCreatableDataSetter, SectionableDataProvider, SectionableDataSetter {

    private final @Nullable String name;
    private final @NotNull DataCompressor compressor;

    public CompressorDataProvider(@Nullable String name, @NotNull DataCompressor dataCompressor) {
        Objects.requireNonNull(dataCompressor, "dataCompressor");
        this.name = name;
        this.compressor = dataCompressor;
    }

    public @Nullable String getName() {
        return this.name;
    }

    @NotNull
    public DataCompressor getCompressor() {
        return this.compressor;
    }

    @NotNull
    public CompressorDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "");
        return new CompressorDataProvider(key, this.compressor);
    }

    @NotNull
    public CompressorDataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "");
        return new CompressorDataProvider(null, this.compressor);
    }

    @NotNull
    public CompressorDataProvider createSection() {
        if (this.name == null) {
            throw new IllegalStateException("No key name set");
        } else {
            this.compressor.compress(this.name);
            return new CompressorDataProvider(this.name, this.compressor);
        }
    }

    public void setString(@NotNull String value) {
        this.compressor.compress(value);
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        if (value == null) {
            this.compressor.compressNull();
        } else {
            this.compressor.compress(value.getWorld()).compress(value.getX()).compress(value.getY()).compress(value.getZ());
        }
    }

    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation value) {
        Objects.requireNonNull(value, "");
        this.compressor.compress(value.getWorld()).compress(value.getX()).compress(value.getZ());
    }

    public void setUUID(@Nullable UUID value) {
        this.compressor.compress(value);
    }

    public void setInt(int value) {
        this.compressor.compress(value);
    }

    public void setLong(long value) {
        this.compressor.compress(value);
    }

    public void setFloat(float value) {
        this.compressor.compress(value);
    }

    public void setDouble(double value) {
        this.compressor.compress(value);
    }

    public void setBoolean(boolean value) {
        this.compressor.compress(value);
    }

    public void setLocation(@Nullable SimpleLocation var1) {
        if (var1 == null) {
            this.compressor.compressNull();
        } else {
            this.compressor.compress(var1);
        }
    }

    public <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        if (!value.isEmpty()) {
            for (E var3 : value) {
                var2.accept(this, var3);
            }
        }
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        if (!value.isEmpty()) {

            for (Map.Entry<K, ? extends V> kEntry : value.entrySet()) {
                K var4 = kEntry.getKey();
                V var6 = kEntry.getValue();

                var2.map(var4, new StringMappedIdSetter((var100) -> new CompressorDataProvider(var100, CompressorDataProvider.this.getCompressor())), var6);
            }
        }
    }
}
