package net.aurika.data.database.nbt;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.dataprovider.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.data.database.dataprovider.*;
import top.auspice.nbt.tag.*;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

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
    public DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key);
        NBTTagCompound var10000 = NBTTagCompound.empty();
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        if (this.element instanceof NBTTagCompound) {
            ((NBTTagCompound) this.element).set(key, (NBTTag) var10000);
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
        SimpleChunkLocation var1 = SimpleChunkLocation.fromString(var10000);
        Intrinsics.checkNotNullExpressionValue(var1, "");
        return var1;
    }

    public @Nullable SimpleLocation asLocation() {
        String var10000 = this.asString(() -> null);
        return var10000 != null ? SimpleLocation.fromString(var10000) : null;
    }

    public int asInt(@NotNull IntSupplier def) {
        return ((NBTTagInt) this.element).valueAsInt();
    }

    public long asLong(@NotNull LongSupplier def) {
        return ((NBTTagLong) this.element).valueAsLong();
    }

    public float asFloat(@NotNull FloatSupplier def) {
        return ((NBTTagFloat) this.element).valueAsFloat();
    }

    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "");
        return ((NBTTagDouble) this.element).valueAsDouble();
    }

    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "");
        return ((NBTTagBool) this.element).valueAsBool();
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(dataProcessor, "");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);

        for (NBTTag<?> o : ((NBTTagList<?>) var10000).value()) {
            Intrinsics.checkNotNull(o);
            dataProcessor.accept(c, new NBTDataProvider(o));
        }

        return c;
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(dataProcessor, "");
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
            dataProcessor.accept(m, var10002, new NBTDataProvider(var7));
        }

        return m;
    }

    public void setSimpleLocation(@NotNull SimpleBlockLocation value) {
        this.setString(value != null ? value.asDataString() : null);
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation value) {
        Objects.requireNonNull(value, "");
        this.setString(value.asDataString());
    }

    public void setLong(long value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagLong.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setString(@NotNull String value) {
        if (value != null) {
            if (this.element instanceof NBTTagList) {
                ((NBTTagList<?>) this.element).addUnknown(NBTTagString.of(value));
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    public void setInt(int value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagInt.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setFloat(float value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagFloat.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setDouble(double value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagDouble.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setBoolean(boolean value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagBool.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        throw new UnsupportedOperationException();
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        throw new UnsupportedOperationException();
    }

    public void setLocation(@Nullable SimpleLocation value) {
        if (value != null) {
            this.setString(value.asDataString());
        }
    }

    public void setUUID(@Nullable UUID value) {
        this.setString(FastUUID.toString(value));
    }
}
