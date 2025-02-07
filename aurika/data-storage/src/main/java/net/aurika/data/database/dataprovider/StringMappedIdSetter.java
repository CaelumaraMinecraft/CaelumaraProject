package net.aurika.data.database.dataprovider;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StringMappedIdSetter implements MappedIdSetter {
    @NotNull
    private final Function<String, SectionCreatableDataSetter> a;
    @Nullable
    private String s;

    public StringMappedIdSetter(@NotNull Function<String, SectionCreatableDataSetter> var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.a = var1;
    }

    private static UnsupportedOperationException a() {
        return new UnsupportedOperationException("This ID type is not supported");
    }

    public @NotNull SectionCreatableDataSetter getValueProvider() {
        if (this.s == null) {
            throw new IllegalStateException("Cannot get value provider before setting the ID");
        } else {
            String var10001 = this.s;
            Intrinsics.checkNotNull(var10001);
            return this.a.apply(var10001);
        }
    }

    public void setString(@NotNull String value) {
        Intrinsics.checkNotNull(value);
        this.s = value;
    }

    public void setInt(int value) {
        this.s = String.valueOf(value);
    }

    public void setLocation(@NotNull SimpleLocation value) {
        throw a();
    }

    public void setSimpleLocation(@NotNull SimpleBlockLocation value) {
        this.s = value != null ? value.asDataString() : null;
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation value) {
        this.s = value == null ? null : value.asDataString();
    }

    public void setLong(long value) {
        this.s = String.valueOf(value);
    }

    public void setFloat(float value) {
        throw a();
    }

    public void setDouble(double value) {
        throw a();
    }

    public void setBoolean(boolean value) {
        throw a();
    }

    public void setUUID(@Nullable UUID value) {
        this.s = FastUUID.toString(value);
    }

    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Intrinsics.checkNotNullParameter(value, "");
        Intrinsics.checkNotNullParameter(var2, "");
        throw a();
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        Intrinsics.checkNotNullParameter(value, "");
        Intrinsics.checkNotNullParameter(var2, "");
        throw a();
    }
}
