package net.aurika.data.database.nbt;

import net.aurika.data.database.dataprovider.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
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

public class NamedNBTDataProvider implements DataProvider, SectionCreatableDataSetter {

    private @Nullable String name;
    private @NotNull NBTTagCompound obj;

    public NamedNBTDataProvider(@Nullable String name, @NotNull NBTTagCompound obj) {
        Objects.requireNonNull(obj, "obj");
        this.name = name;
        this.obj = obj;
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @NotNull
    public NBTTagCompound getObj() {
        return this.obj;
    }

    public void setObj(@NotNull NBTTagCompound var1) {
        Objects.requireNonNull(var1, "");
        this.obj = var1;
    }

    private NBTTagCompound a() {
        return this.name == null ? this.obj : this.obj.getCompound(this.name);
    }

    private String accessName() {
        String var10000 = this.name;
        if (var10000 == null) {
            throw new IllegalStateException("No key name set");
        } else {
            return var10000;
        }
    }

    @Override
    public @NotNull NamedNBTDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return new NamedNBTDataProvider(key, this.obj);
    }

    @Override
    public @NotNull NamedNBTDataProvider asSection() {
        NBTTagCompound var10003 = this.a();
        if (var10003 == null) {
            var10003 = NBTTagCompound.empty();
            Objects.requireNonNull(var10003, "");
        }

        return new NamedNBTDataProvider(null, var10003);
    }

    @Override
    @Nullable
    public String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "");
        String var10000 = this.obj.get(this.accessName(), NBTTagType.STRING);
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000;
    }

    @Override
    public @Nullable UUID asUUID() {
        String s = this.asString();
        return s != null ? FastUUID.fromString(s) : null;
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        String s = this.asString();
        return s != null ? SimpleBlockLocation.fromString(s) : null;
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        String s = this.asString();
        return s != null ? SimpleChunkLocation.fromString(s) : null;
    }

    @Override
    public @Nullable SimpleLocation asLocation() {
        String s = this.asString();
        return s != null ? SimpleLocation.fromString(s) : null;
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        Integer i = this.obj.get(this.accessName(), NBTTagType.INT);
        return i == null ? def.getAsInt() : i;
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def, "def");
        Long l = this.obj.get(this.accessName(), NBTTagType.LONG);
        return l == null ? def.getAsLong() : l;
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "def");
        Float f = this.obj.get(this.accessName(), NBTTagType.FLOAT);
        return f == null ? def.getAsFloat() : f;
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "def");
        Double d = this.obj.get(this.accessName(), NBTTagType.DOUBLE);
        return d == null ? def.getAsDouble() : d;
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "def");
        Byte b = this.obj.get(this.accessName(), NBTTagType.BOOL);
        boolean var2;
        if (b != null) {
            if (b == 0) {
                return false;
            }

            var2 = true;
        } else {
            var2 = false;
        }

        return var2;
    }

    @Override
    public <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(dataProcessor, "dataProcessor");
        NBTTagList<?> var10000 = this.obj.get(this.accessName(), Fn.cast(NBTTagType.LIST));
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

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(m, "m");
        Objects.requireNonNull(dataProcessor, "dataProcessor");
        NBTTagCompound var10000 = this.a();
        if (var10000 != null) {
            Map<String, ? extends NBTTag<?>> var7 = var10000.value();
            Objects.requireNonNull(var7);

            for (Map.Entry<String, ? extends NBTTag<?>> entry : var7.entrySet()) {
                String var5 = entry.getKey();
                NBTTag<?> var6 = entry.getValue();
                Objects.requireNonNull(var5);
                NBTTag var8 = NBTTagType.fromJava(var5).construct(var5);
                Objects.requireNonNull(var8);
                NBTDataProvider var10002 = new NBTDataProvider(var8);
                Objects.requireNonNull(var6);
                dataProcessor.accept(m, var10002, createProvider$core(var6));
            }
        }
        return m;
    }

    @Override
    public void setString(@Nullable String value) {
        if (value != null) this.obj.set(this.accessName(), value);
    }

    @Override
    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        if (value != null) this.setString(value.asDataString());
    }

    @Override
    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation value) {
        if (value != null) this.setString(value.asDataString());
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) this.obj.set(this.accessName(), FastUUID.toString(value));
    }

    @Override
    public void setInt(int value) {
        this.obj.set(this.accessName(), value);
    }

    @Override
    public void setLong(long value) {
        this.obj.set(this.accessName(), value);
    }

    @Override
    public void setFloat(float value) {
        this.obj.set(this.accessName(), value);
    }

    @Override
    public void setDouble(double value) {
        this.obj.set(this.accessName(), value);
    }

    @Override
    public void setBoolean(boolean value) {
        this.obj.set(this.accessName(), value);
    }

    @Override
    public void setLocation(@Nullable SimpleLocation value) {
        if (value != null) this.setString(value.asDataString());
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(var2, "");
        if (!value.isEmpty()) {
            NBTTagList<?> var10000 = NBTTagList.unknownEmpty();
            Objects.requireNonNull(var10000, "");

            for (V var4 : value) {
                var2.accept(createProvider$core(var10000), var4);
            }

            this.obj.set(this.accessName(), var10000);
        }
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(value, "value");
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

            this.obj.set(this.accessName(), (NBTTag<?>) var3);
        }
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        NBTTagCompound var10000 = NBTTagCompound.empty();
        Objects.requireNonNull(var10000, "");
        NBTTagCompound var1 = var10000;
        var10000 = this.obj;
        String var10001 = this.name;
        Objects.requireNonNull(var10001);
        var10000.set(var10001, (NBTTag<?>) var1);
        return new NamedNBTDataProvider(null, var1);
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "");
        if (this.name != null) {
            throw new IllegalStateException("Previous name not handled: " + this.name + " -> " + key);
        } else {
            NBTTagCompound var10000 = NBTTagCompound.empty();
            Objects.requireNonNull(var10000);
            this.obj.set(key, (NBTTag<?>) var10000);
            return new NamedNBTDataProvider(null, var10000);
        }
    }

    @NotNull
    public static DataProvider createProvider$core(@NotNull NBTTag<?> tag) {
        Objects.requireNonNull(tag, "tag");
        return tag instanceof NBTTagCompound ? new NamedNBTDataProvider(null, (NBTTagCompound) tag) : new NBTDataProvider(tag);
    }
}
