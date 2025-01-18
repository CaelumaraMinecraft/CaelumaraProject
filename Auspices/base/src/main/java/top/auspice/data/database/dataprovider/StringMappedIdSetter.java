package top.auspice.data.database.dataprovider;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public  class StringMappedIdSetter implements MappedIdSetter {
    @NotNull
    private final Function1<String, SectionCreatableDataSetter> a;
    @Nullable
    private String b;

    public StringMappedIdSetter(@NotNull Function1<String, ? extends SectionCreatableDataSetter> var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.a = var1;
    }

    private static Void a() {
        throw new UnsupportedOperationException("This ID type is not supported");
    }

    @NotNull
    public final SectionCreatableDataSetter getValueProvider() {
        if (this.b == null) {
            throw new IllegalStateException("Cannot get value provider before setting the ID");
        } else {
            Function1 var10000 = this.a;
            String var10001 = this.b;
            Intrinsics.checkNotNull(var10001);
            return (SectionCreatableDataSetter)var10000.invoke(var10001);
        }
    }

    public final void setString(@Nullable String var1) {
        Intrinsics.checkNotNull(var1);
        this.b = var1;
    }

    public final void setInt(int var1) {
        this.b = String.valueOf(var1);
    }

    @NotNull
    public final Void setLocation(@Nullable Location var1) {
        a();
        throw new KotlinNothingValueException();
    }

    public final void setSimpleLocation(@Nullable SimpleLocation var1) {
        this.b = var1 != null ? var1.asDataString() : null;
    }

    public final void setSimpleChunkLocation(@NotNull SimpleChunkLocation var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.b = var1.asDataString();
    }

    public final void setLong(long var1) {
        this.b = String.valueOf(var1);
    }

    @NotNull
    public final Void setFloat(float var1) {
        a();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public final Void setDouble(double var1) {
        a();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public final Void setBoolean(boolean var1) {
        a();
        throw new KotlinNothingValueException();
    }

    public final void setUUID(@Nullable UUID var1) {
        this.b = FastUUID.toString(var1);
    }

    @NotNull
    public final <V> Void setCollection(@NotNull Collection<? extends V> var1, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        a();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public final <K, V> Void setMap(@NotNull Map<K, ? extends V> var1, @NotNull MappingSetterHandler<K, V> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        a();
        throw new KotlinNothingValueException();
    }
}
