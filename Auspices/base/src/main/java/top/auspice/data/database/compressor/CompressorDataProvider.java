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
    public final CompressorDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "");
        return new CompressorDataProvider(key, this.compressor);
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

    public final void setString(@Nullable String s) {
        this.compressor.compress(s);
    }

    public final void setSimpleLocation(@Nullable SimpleBlockLocation blockLocation) {
        if (blockLocation == null) {
            this.compressor.compressNull();
        } else {
            this.compressor.compress(blockLocation.getWorld()).compress(blockLocation.getX()).compress(blockLocation.getY()).compress(blockLocation.getZ());
        }
    }

    public final void setSimpleChunkLocation(@NotNull SimpleChunkLocation chunkLocation) {
        Objects.requireNonNull(chunkLocation, "");
        this.compressor.compress(chunkLocation.getWorld()).compress(chunkLocation.getX()).compress(chunkLocation.getZ());
    }

    public final void setUUID(@Nullable UUID uuid) {
        this.compressor.compress(uuid);
    }

    public final void setInt(int var1) {
        this.compressor.compress(var1);
    }

    public final void setLong(long l) {
        this.compressor.compress(l);
    }

    public final void setFloat(float f) {
        this.compressor.compress(f);
    }

    public final void setDouble(double d) {
        this.compressor.compress(d);
    }

    public final void setBoolean(boolean b) {
        this.compressor.compress(b);
    }

    public final void setLocation(@Nullable OldLocation var1) {
        if (var1 == null) {
            this.compressor.compressNull();
        } else {
            this.compressor.compress(var1);
        }
    }

    public final <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(c, "");
        Objects.requireNonNull(var2, "");
        if (!c.isEmpty()) {
            for (V var3 : c) {
                var2.accept(this, var3);
            }

        }
    }

    public final <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(var2, "");
        if (!m.isEmpty()) {

            for (Map.Entry<K, ? extends V> kEntry : m.entrySet()) {
                K var4 = kEntry.getKey();
                V var6 = kEntry.getValue();

                var2.map(var4, new StringMappedIdSetter((var100) -> new CompressorDataProvider(var100, CompressorDataProvider.this.getCompressor())), var6);
            }

        }
    }
}
