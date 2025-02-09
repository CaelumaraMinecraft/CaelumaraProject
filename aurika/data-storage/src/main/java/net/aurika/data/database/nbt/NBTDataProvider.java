package net.aurika.data.database.nbt;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.api.dataprovider.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.nbt.tag.*;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

public class NBTDataProvider implements DataProvider, SectionCreatableDataSetter {

    private final @NotNull NBTTag<?> element;

    public NBTDataProvider(@NotNull NBTTag<?> element) {
        Objects.requireNonNull(element);
        this.element = element;
    }

    public @NotNull NBTTag<?> getElement$core() {
        return this.element;
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
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

    @Override
    public @NotNull SectionableDataSetter createSection() {
        NBTTagCompound var10000 = NBTTagCompound.empty();
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(var10000);
            return new NamedNBTDataProvider(null, var10000);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public @NotNull DataProvider get(@NotNull String key) {
        Objects.requireNonNull(key);
        return new NamedNBTDataProvider(key, NBTTagType.COMPOUND.cast(this.element));
    }

    @Override
    public @NotNull NBTDataProvider asSection() {
        return this;
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
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
    public @Nullable UUID asUUID() {
        String s = this.asString();
        return s == null ? null : FastUUID.fromString(s);
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        String s = this.asString();
        return s != null ? SimpleBlockLocation.fromString(s) : null;
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        String s = this.asString();
        return s == null ? null : SimpleChunkLocation.fromString(s);
    }

    @Override
    public @Nullable SimpleLocation asLocation() {
        String s = this.asString(() -> null);
        return s != null ? SimpleLocation.fromString(s) : null;
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        return ((NBTTagInt) this.element).valueAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        return ((NBTTagLong) this.element).valueAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        return ((NBTTagFloat) this.element).valueAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "def");
        return ((NBTTagDouble) this.element).valueAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "def");
        return ((NBTTagBool) this.element).valueAsBool();
    }

    @Override
    public <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(dataProcessor, "dataProcessor");
        NBTTag<?> var10000 = this.element;
        Intrinsics.checkNotNull(var10000);

        for (NBTTag<?> o : ((NBTTagList<?>) var10000).value()) {
            Intrinsics.checkNotNull(o);
            dataProcessor.accept(c, new NBTDataProvider(o));
        }

        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
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

    @Override
    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        this.setString(value != null ? value.asDataString() : null);
    }

    @Override
    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation value) {
        Objects.requireNonNull(value, "");
        this.setString(value.asDataString());
    }

    @Override
    public void setLong(long value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagLong.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setString(@Nullable String value) {
        if (value != null) {
            if (this.element instanceof NBTTagList) {
                ((NBTTagList<?>) this.element).addUnknown(NBTTagString.of(value));
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    @Override
    public void setInt(int value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagInt.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setFloat(float value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagFloat.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setDouble(double value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagDouble.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setBoolean(boolean value) {
        if (this.element instanceof NBTTagList) {
            ((NBTTagList<?>) this.element).addUnknown(NBTTagBool.of(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocation(@Nullable SimpleLocation value) {
        if (value != null) {
            this.setString(value.asDataString());
        }
    }

    @Override
    public void setUUID(@NotNull UUID value) {
        this.setString(FastUUID.toString(value));
    }
}
