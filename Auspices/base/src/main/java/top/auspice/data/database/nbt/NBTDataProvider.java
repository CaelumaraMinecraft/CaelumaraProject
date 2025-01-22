package top.auspice.data.database.nbt;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.database.dataprovider.*;
import top.auspice.nbt.tag.*;
import top.auspice.server.location.Location;
import top.auspice.server.location.OldLocation;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.internal.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class NBTDataProvider implements DataProvider, SectionCreatableDataSetter {
    @NotNull
    private final NBTTag<?> element;

    public NBTDataProvider(@NotNull NBTTag<?> element) {
        Objects.requireNonNull(element);
        this.element = element;
    }

    @NotNull
    public NBTTag<?> getElement$core() {
        return this.element;
    }

    @NotNull
    public DataProvider createSection(@NotNull String var1) {
        Objects.requireNonNull(var1);
        NBTTagCompound var10000 = NBTTagCompound.empty();
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        if (this.element instanceof NBTTagCompound) {
            ((NBTTagCompound) this.element).set(var1, (NBTTag) var10000);
            return new NBTDataProvider(var10000);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    public SectionableDataSetter createSection() {
        NBTTagCompound var10000 = NBTTagCompound.empty();
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(var10000);
            return new NamedNBTDataProvider(null, var10000);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    public DataProvider get(@NotNull String key) {
        Objects.requireNonNull(key);
        return new NamedNBTDataProvider(key, NBTTagType.COMPOUND.cast(this.element));
    }

    @NotNull
    public NBTDataProvider asSection() {
        return this;
    }

    @Nullable
    public String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def);
        NBTTag<?> var2 = this.element;
        NBTTagString var10000 = var2 instanceof NBTTagString ? (NBTTagString) var2 : null;
        String var3;
        if (var10000 != null) {
            var3 = var10000.value();
            if (var3 != null) {
                return var3;
            }
        }

        var3 = def.get();
        return var3;
    }

    @Override
    @NotNull
    public UUID asUUID() {

        UUID uuid = FastUUID.fromString(this.asString(() -> {
            throw new IllegalStateException();
        }));
        Intrinsics.checkNotNullExpressionValue(uuid, "");
        return uuid;
    }

    @Nullable
    public SimpleBlockLocation asSimpleLocation() {
        String var10000 = this.asString();
        return var10000 != null ? SimpleBlockLocation.fromString(var10000) : null;
    }

    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {

        String var10000 = this.asString(() -> {
            throw new IllegalStateException();
        });
        Intrinsics.checkNotNull(var10000);
        SimpleChunkLocation var1 = SimpleChunkLocation.fromString(var10000);
        Intrinsics.checkNotNullExpressionValue(var1, "");
        return var1;
    }

    @Nullable
    public Location asLocation() {
        String var10000 = this.asString(() -> null);
        return var10000 != null ? LocationUtils.fromString(var10000) : null;
    }

    public int asInt(@NotNull Supplier<Integer> def) {
        Objects.requireNonNull(def, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);
        return ((NBTTagInt) var10000).valueAsInt();
    }

    public long asLong(@NotNull Supplier<Long> def) {
        Objects.requireNonNull(def, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);
        return ((NBTTagLong) var10000).valueAsLong();
    }

    public float asFloat(@NotNull Supplier<Float> def) {
        Objects.requireNonNull(def, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);
        return ((NBTTagFloat) var10000).valueAsFloat();
    }

    public double asDouble(@NotNull Supplier<Double> def) {
        Objects.requireNonNull(def, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);
        return ((NBTTagDouble) var10000).valueAsDouble();
    }

    public boolean asBoolean(@NotNull Supplier<Boolean> def) {
        Objects.requireNonNull(def, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);
        return ((NBTTagBool) var10000).valueAsBool();
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C var1, @NotNull BiConsumer<C, SectionableDataGetter> var2) {
        Objects.requireNonNull(var1, "");
        Objects.requireNonNull(var2, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);

        for (NBTTag<?> o : ((NBTTagList<?>) var10000).value()) {
            Intrinsics.checkNotNull(o);
            var2.accept(var1, new NBTDataProvider(o));
        }

        return var1;
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M var1, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> var2) {
        Objects.requireNonNull(var1, "");
        Objects.requireNonNull(var2, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);
        Map<String, ? extends NBTTag<?>> var6 = ((NBTTagCompound) var10000).value();
        Intrinsics.checkNotNullExpressionValue(var6, "");

        for (Map.Entry<String, ? extends NBTTag<?>> o : var6.entrySet()) {
            String var5 = o.getKey();
            NBTTag<?> var7 = o.getValue();
            NBTTagString var10004 = NBTTagString.of(var5);
            Intrinsics.checkNotNullExpressionValue(var10004, "");
            NBTDataProvider var10002 = new NBTDataProvider(var10004);
            Intrinsics.checkNotNull(var7);
            var2.accept(var1, var10002, new NBTDataProvider(var7));
        }

        return var1;
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation blockLocation) {
        this.setString(blockLocation != null ? blockLocation.asDataString() : null);
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation chunkLocation) {
        Objects.requireNonNull(chunkLocation, "");
        this.setString(chunkLocation.asDataString());
    }

    public void setLong(long l) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagLong.of(l));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setString(@Nullable String s) {
        if (s != null) {
            if (this.element instanceof NBTTagList) {
                ((NBTTagList<?>) this.element).addUnknown(NBTTagString.of(s));
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    public void setInt(int var1) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagInt.of(var1));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setFloat(float f) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagFloat.of(f));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setDouble(double d) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagDouble.of(d));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setBoolean(boolean b) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagBool.of(b));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(c, "");
        Objects.requireNonNull(var2, "");
        throw new UnsupportedOperationException();
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(var2, "");
        throw new UnsupportedOperationException();
    }

    public void setLocation(@Nullable OldLocation var1) {
        if (var1 != null) {
            this.setString(LocationUtils.toString(var1));
        }
    }

    public void setUUID(@Nullable UUID uuid) {
        this.setString(FastUUID.toString(uuid));
    }
}
