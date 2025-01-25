
package net.aurika.data.database.nbt;

import net.aurika.data.database.dataprovider.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.data.database.dataprovider.*;
import top.auspice.nbt.tag.NBTTag;
import top.auspice.nbt.tag.NBTTagCompound;
import top.auspice.nbt.tag.NBTTagList;
import top.auspice.nbt.tag.NBTTagType;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.Fn;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

public final class NamedNBTDataProvider implements DataProvider, SectionCreatableDataSetter {
    @Nullable
    private String a;
    @NotNull
    private NBTTagCompound b;

    public NamedNBTDataProvider(@Nullable String var1, @NotNull NBTTagCompound var2) {
        Objects.requireNonNull(var2, "");
        this.a = var1;
        this.b = var2;
    }

    @Nullable
    public String getName() {
        return this.a;
    }

    public void setName(@Nullable String var1) {
        this.a = var1;
    }

    @NotNull
    public NBTTagCompound getObj() {
        return this.b;
    }

    public void setObj(@NotNull NBTTagCompound var1) {
        Objects.requireNonNull(var1, "");
        this.b = var1;
    }

    private NBTTagCompound a() {
        return this.a == null ? this.b : this.b.getCompound(this.a);
    }

    private String b() {
        String var10000 = this.a;
        if (var10000 == null) {
            throw new IllegalStateException("No key name set");
        } else {
            return var10000;
        }
    }

    @NotNull
    public DataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "");
        return new NamedNBTDataProvider(key, this.b);
    }

    @NotNull
    public DataProvider asSection() {
        NBTTagCompound var10003 = this.a();
        if (var10003 == null) {
            var10003 = NBTTagCompound.empty();
            Objects.requireNonNull(var10003, "");
        }

        return new NamedNBTDataProvider(null, var10003);
    }

    @Nullable
    public String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "");
        String var10000 = this.b.get(this.b(), NBTTagType.STRING);
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000;
    }

    @Override
    @Nullable
    public UUID asUUID() {
        String var10000 = this.asString();
        return var10000 != null ? FastUUID.fromString(var10000) : null;
    }

    @Nullable
    public SimpleBlockLocation asSimpleLocation() {
        String var10000 = this.asString();
        return var10000 != null ? SimpleBlockLocation.fromString(var10000) : null;
    }

    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        String var10000 = this.asString();
        return var10000 != null ? SimpleChunkLocation.fromString(var10000) : null;
    }

    public @Nullable SimpleLocation asLocation() {
        String var10000 = this.asString();
        return var10000 != null ? SimpleLocation.fromString(var10000) : null;
    }

    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "");
        Integer var10000 = this.b.get(this.b(), NBTTagType.INT);
        return var10000 == null ? def.getAsInt() : var10000;
    }

    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def, "");
        Long var10000 = this.b.get(this.b(), NBTTagType.LONG);
        return var10000 == null ? def.getAsLong() : var10000;
    }

    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "");
        Float var10000 = this.b.get(this.b(), NBTTagType.FLOAT);
        return var10000 == null ? def.getAsFloat() : var10000;
    }

    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "");
        Double var10000 = this.b.get(this.b(), NBTTagType.DOUBLE);
        return var10000 == null ? def.getAsDouble() : var10000;
    }

    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "");
        Byte var10000 = this.b.get(this.b(), NBTTagType.BOOL);
        boolean var2;
        if (var10000 != null) {
            if (var10000 == 0) {
                return false;
            }

            var2 = true;
        } else {
            var2 = false;
        }

        return var2;
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c, "");
        Objects.requireNonNull(dataProcessor, "");
        NBTTagList<?> var10000 = this.b.get(this.b(), Fn.cast(NBTTagType.LIST));
        if (var10000 == null) {
            return c;
        } else {

            for (NBTTag<?> o : var10000.value()) {
                Objects.requireNonNull(o);
                dataProcessor.accept(c, createProvider$core(o));
            }

            return c;
        }
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(m);
        Objects.requireNonNull(dataProcessor);
        NBTTagCompound var10000 = this.a();
        if (var10000 == null) {
            return m;
        } else {
            Map<String, ? extends NBTTag<?>> var7 = var10000.value();
            Objects.requireNonNull(var7);

            for (Map.Entry<String, ? extends NBTTag<?>> stringEntry : var7.entrySet()) {
                String var5 = stringEntry.getKey();
                NBTTag<?> var6 = stringEntry.getValue();
                Objects.requireNonNull(var5);
                NBTTag var8 = NBTTagType.fromJava(var5).construct(var5);
                Objects.requireNonNull(var8);
                NBTDataProvider var10002 = new NBTDataProvider(var8);
                Objects.requireNonNull(var6);
                dataProcessor.accept(m, var10002, createProvider$core(var6));
            }

            return m;
        }
    }

    public void setString(@NotNull String value) {
        if (value != null) {
            this.b.set(this.b(), value);
        }
    }

    public void setSimpleLocation(@NotNull SimpleBlockLocation value) {
        this.setString(value != null ? value.asDataString() : null);
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation value) {
        Objects.requireNonNull(value, "");
        this.setString(value.asDataString());
    }

    public void setUUID(@Nullable UUID value) {
        if (value != null) {
            this.b.set(this.b(), FastUUID.toString(value));
        }
    }

    public void setInt(int value) {
        this.b.set(this.b(), value);
    }

    public void setLong(long value) {
        this.b.set(this.b(), value);
    }

    public void setFloat(float value) {
        this.b.set(this.b(), value);
    }

    public void setDouble(double value) {
        this.b.set(this.b(), value);
    }

    public void setBoolean(boolean value) {
        this.b.set(this.b(), value);
    }

    public void setLocation(@NotNull SimpleLocation value) {
        if (value != null) {
            this.setString(value.asDataString());
        }
    }

    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        if (!value.isEmpty()) {
            NBTTagList var10000 = NBTTagList.unknownEmpty();
            Objects.requireNonNull(var10000, "");
            NBTTagList var3 = var10000;

            for (V var4 : value) {
                var2.accept(createProvider$core(var3), var4);
            }

            this.b.set(this.b(), var3);
        }
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        if (!value.isEmpty()) {
            NBTTagCompound var10000 = NBTTagCompound.empty();
            Objects.requireNonNull(var10000, "");
            final NBTTagCompound var3 = var10000;

            for (Map.Entry<K, ? extends V> kEntry : value.entrySet()) {
                K var5 = kEntry.getKey();
                V var7 = kEntry.getValue();

                var2.map(var5, new StringMappedIdSetter((s) -> new NamedNBTDataProvider(s, var3)), var7);
            }

            this.b.set(this.b(), (NBTTag<?>) var3);
        }
    }

    @NotNull
    public SectionableDataSetter createSection() {
        NBTTagCompound var10000 = NBTTagCompound.empty();
        Objects.requireNonNull(var10000, "");
        NBTTagCompound var1 = var10000;
        var10000 = this.b;
        String var10001 = this.a;
        Objects.requireNonNull(var10001);
        var10000.set(var10001, (NBTTag<?>) var1);
        return new NamedNBTDataProvider(null, var1);
    }

    @NotNull
    public DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "");
        if (this.a != null) {
            throw new IllegalStateException("Previous name not handled: " + this.a + " -> " + key);
        } else {
            NBTTagCompound var10000 = NBTTagCompound.empty();
            Objects.requireNonNull(var10000);
            this.b.set(key, (NBTTag<?>) var10000);
            return new NamedNBTDataProvider(null, var10000);
        }
    }

    @NotNull
    public static DataProvider createProvider$core(@NotNull NBTTag<?> var1) {
        Objects.requireNonNull(var1, "");
        return var1 instanceof NBTTagCompound ? new NamedNBTDataProvider(null, (NBTTagCompound) var1) : new NBTDataProvider(var1);
    }
}
