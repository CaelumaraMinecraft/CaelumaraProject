package top.auspice.data.database.dataprovider;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.internal.uuid.FastUUID;

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

    public void setString(@Nullable String s) {
        Intrinsics.checkNotNull(s);
        this.s = s;
    }

    public void setInt(int var1) {
        this.s = String.valueOf(var1);
    }

    public void setLocation(@Nullable SimpleLocation var1) {
        throw a();
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation var1) {
        this.s = var1 != null ? var1.asDataString() : null;
    }

    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation var1) {
        this.s = var1 == null ? null : var1.asDataString();
    }

    public void setLong(long l) {
        this.s = String.valueOf(l);
    }

    public void setFloat(float f) {
        throw a();
    }

    public void setDouble(double d) {
        throw a();
    }

    public void setBoolean(boolean b) {
        throw a();
    }

    public void setUUID(@Nullable UUID var1) {
        this.s = FastUUID.toString(var1);
    }

    public <V> void setCollection(@NotNull Collection<? extends V> var1, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        throw a();
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> var1, @NotNull MappingSetterHandler<K, V> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        throw a();
    }
}
