package net.aurika.data.database.compressor;

import net.aurika.data.api.structure.DataUnits;
import net.aurika.data.compressor.DataCompressor;
import net.aurika.data.database.dataprovider.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return name;
    }

    @NotNull
    public DataCompressor getCompressor() {
        return compressor;
    }

    @Override
    public @NotNull CompressorDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return new CompressorDataProvider(key, compressor);
    }

    @Override
    public @NotNull CompressorDataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return new CompressorDataProvider(null, compressor);
    }

    @Override
    public @NotNull CompressorDataProvider createSection() {
        if (name == null) {
            throw new IllegalStateException("No key name set");
        } else {
            compressor.compress(name);
            return new CompressorDataProvider(name, compressor);
        }
    }

    @Override
    public void setInt(int value) {
        compressor.compress(value);
    }

    @Override
    public void setLong(long value) {
        compressor.compress(value);
    }

    @Override
    public void setFloat(float value) {
        compressor.compress(value);
    }

    @Override
    public void setDouble(double value) {
        compressor.compress(value);
    }

    @Override
    public void setBoolean(boolean value) {
        compressor.compress(value);
    }

    @Override
    public void setString(@Nullable String value) {
        compressor.compress(value);
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        compressor.compress(value);
    }

    @Override
    public void setObject(@Nullable DataUnits value) {
        compressor.compress(value);
    }

    @Override
    public <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> handler) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(handler, "");
        if (!value.isEmpty()) {
            for (E var3 : value) {
                handler.accept(this, var3);
            }
        }
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "");
        if (!value.isEmpty()) {

            for (Map.Entry<K, ? extends V> kEntry : value.entrySet()) {
                K var4 = kEntry.getKey();
                V var6 = kEntry.getValue();

                handler.map(var4, new StringMappedIdSetter((var100) -> new CompressorDataProvider(var100, CompressorDataProvider.this.getCompressor())), var6);
            }
        }
    }
}
