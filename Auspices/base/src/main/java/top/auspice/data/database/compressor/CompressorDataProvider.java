package top.auspice.data.database.compressor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.compressor.DataCompressor;
import top.auspice.data.database.dataprovider.*;
import top.auspice.server.location.OldLocation;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public class CompressorDataProvider implements SectionCreatableDataSetter, SectionableDataProvider, SectionableDataSetter {
    @Nullable
    private final String name;
    @NotNull
    private final DataCompressor compressor;

    public CompressorDataProvider(@Nullable String var1, @NotNull DataCompressor var2) {
        Objects.requireNonNull(var2, "");
        this.name = var1;
        this.compressor = var2;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final DataCompressor getCompressor() {
        return this.compressor;
    }

    @NotNull
    public final CompressorDataProvider get(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        return new CompressorDataProvider(var1, this.compressor);
    }

    @NotNull
    public final CompressorDataProvider createSection(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        return new CompressorDataProvider(null, this.compressor);
    }

    @NotNull
    public final CompressorDataProvider createSection() {
        if (this.name == null) {
            throw new IllegalStateException("No key name set");
        } else {
            this.compressor.compress(this.name);
            return new CompressorDataProvider(this.name, this.compressor);
        }
    }

    public final void setString(@Nullable String var1) {
        this.compressor.compress(var1);
    }

    public final void setSimpleLocation(@Nullable SimpleBlockLocation var1) {
        if (var1 == null) {
            this.compressor.compressNull();
        } else {
            this.compressor.compress(var1.getWorld()).compress(var1.getX()).compress(var1.getY()).compress(var1.getZ());
        }
    }

    public final void setSimpleChunkLocation(@NotNull SimpleChunkLocation var1) {
        Objects.requireNonNull(var1, "");
        this.compressor.compress(var1.getWorld()).compress(var1.getX()).compress(var1.getZ());
    }

    public final void setUUID(@Nullable UUID var1) {
        this.compressor.compress(var1);
    }

    public final void setInt(int var1) {
        this.compressor.compress(var1);
    }

    public final void setLong(long var1) {
        this.compressor.compress(var1);
    }

    public final void setFloat(float var1) {
        this.compressor.compress(var1);
    }

    public final void setDouble(double var1) {
        this.compressor.compress(var1);
    }

    public final void setBoolean(boolean var1) {
        this.compressor.compress(var1);
    }

    public final void setLocation(@Nullable OldLocation var1) {
        if (var1 == null) {
            this.compressor.compressNull();
        } else {
            this.compressor.compress(var1);
        }
    }

    public final <V> void setCollection(@NotNull Collection<? extends V> var1, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(var1, "");
        Objects.requireNonNull(var2, "");
        if (!var1.isEmpty()) {
            for (V var3 : var1) {
                var2.accept(this, var3);
            }

        }
    }

    public final <K, V> void setMap(@NotNull Map<K, ? extends V> var1, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(var1, "");
        Objects.requireNonNull(var2, "");
        if (!var1.isEmpty()) {

            for (Map.Entry<K, ? extends V> kEntry : var1.entrySet()) {
                K var4 = kEntry.getKey();
                V var6 = kEntry.getValue();

                var2.map(var4, new StringMappedIdSetter((var100) -> new CompressorDataProvider(var100, CompressorDataProvider.this.getCompressor())), var6);
            }

        }
    }
}
