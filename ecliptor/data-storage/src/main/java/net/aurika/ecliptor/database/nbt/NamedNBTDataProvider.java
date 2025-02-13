package net.aurika.ecliptor.database.nbt;

import net.aurika.ecliptor.api.structured.FunctionsDataStructSchema;
import net.aurika.ecliptor.database.dataprovider.*;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
import net.aurika.util.uuid.FastUUID;
import net.kyori.adventure.nbt.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

public class NamedNBTDataProvider implements DataProvider, SectionCreatableDataSetter {

    private @Nullable String name;
    private @NotNull CompoundBinaryTag obj;

    public NamedNBTDataProvider(@Nullable String name, @NotNull CompoundBinaryTag obj) {
        Objects.requireNonNull(obj, "obj");
        this.name = name;
        this.obj = obj;
    }

    public @Nullable String name() {
        return name;
    }

    public void name(@Nullable String name) {
        this.name = name;
    }

    public @NotNull CompoundBinaryTag obj() {
        return obj;
    }

    public void obj(@NotNull CompoundBinaryTag obj) {
        Objects.requireNonNull(obj, "obj");
        this.obj = obj;
    }

    private CompoundBinaryTag _compound() {
        return name == null ? obj : obj.getCompound(name);
    }

    private String _name() {
        String name = this.name;
        if (name == null) {
            throw new IllegalStateException("No key name set");
        } else {
            return name;
        }
    }

    @Override
    public @NotNull NamedNBTDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return new NamedNBTDataProvider(key, obj);
    }

    @Override
    public @NotNull NamedNBTDataProvider asSection() {
        CompoundBinaryTag var10003 = _compound();
        if (var10003 == null) {
            var10003 = CompoundBinaryTag.empty();
        }

        return new NamedNBTDataProvider(null, var10003);
    }

    @Override
    @Nullable
    public String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "def");
        BinaryTag tag = obj.get(_name());
        return tag != null ? ((StringBinaryTag) tag).value() : def.get();
    }

    @Override
    public @Nullable UUID asUUID() {
        String s = asString();
        return s != null ? FastUUID.fromString(s) : null;
    }

    @Override
    public <T> T asObject(FunctionsDataStructSchema<T> template) {
        return null;
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        BinaryTag tag = obj.get(_name());
        return tag != null ? ((IntBinaryTag) tag).value() : def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def, "def");
        BinaryTag tag = obj.get(_name());
        return tag != null ? ((LongBinaryTag) tag).value() : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "def");
        BinaryTag tag = obj.get(_name());
        return tag != null ? ((FloatBinaryTag) tag).value() : def.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "def");
        BinaryTag tag = obj.get(_name());
        return tag != null ? ((DoubleBinaryTag) tag).value() : def.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "def");
        BinaryTag tag = obj.get(_name());
        return tag != null ? ((ByteBinaryTag) tag).value() != 0 : def.getAsBoolean();
    }

    @Override
    public <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(handler, "dataProcessor");
        BinaryTag tag = obj.get(_name());
        if (tag != null) {
            for (BinaryTag eTag : ((ListBinaryTag) tag)) {
                handler.accept(c, createProvider$core(eTag));
            }
        }
        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Objects.requireNonNull(m, "m");
        Objects.requireNonNull(handler, "dataProcessor");
        CompoundBinaryTag compound = _compound();
        if (compound != null) {
            for (Map.Entry<String, ? extends BinaryTag> entry : compound) {
                handler.accept(m, new NBTDataProvider(StringBinaryTag.stringBinaryTag(entry.getKey())), createProvider$core(entry.getValue()));
            }
        }
        return m;
    }

    @Override
    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        if (value != null) setString(value.asDataString());
    }

    @Override
    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation value) {
        if (value != null) setString(value.asDataString());
    }

    @Override
    public void setInt(int value) {
        obj = obj.putInt(_name(), value);
    }

    @Override
    public void setLong(long value) {
        obj = obj.putLong(_name(), value);
    }

    @Override
    public void setFloat(float value) {
        obj = obj.putFloat(_name(), value);
    }

    @Override
    public void setDouble(double value) {
        obj = obj.putDouble(_name(), value);
    }

    @Override
    public void setBoolean(boolean value) {
        obj = obj.putBoolean(_name(), value);
    }

    @Override
    public void setString(@Nullable String value) {
        if (value != null) obj = obj.put(_name(), StringBinaryTag.stringBinaryTag(value));
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) obj = obj.putString(_name(), FastUUID.toString(value));
    }

    @Override
    public void setObject(@Nullable SetStructuredData value) {
        if (value != null) setString();
    }

    @Override
    public void setLocation(@Nullable SimpleLocation value) {
        if (value != null) setString(value.asDataString());
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "");
        if (!value.isEmpty()) {
            ListBinaryTag var10000 = ListBinaryTag.empty();
            Objects.requireNonNull(var10000, "");

            for (V var4 : value) {
                handler.accept(createProvider$core(var10000), var4);
            }

            obj.put(_name(), var10000);
        }
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "");
        if (!value.isEmpty()) {
            final CompoundBinaryTag var3 = CompoundBinaryTag.empty();

            for (Map.Entry<K, ? extends V> entry : value.entrySet()) {
                handler.map(
                        entry.getKey(),
                        new StringMappedIdSetter((s) -> new NamedNBTDataProvider(s, var3)),
                        entry.getValue()
                );
            }

            obj = obj.put(_name(), var3);
        }
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        CompoundBinaryTag sub = CompoundBinaryTag.empty();
        String name = this.name;
        if (name == null) throw new IllegalStateException("No key name set");
        obj = obj.put(name, sub);
        return new NamedNBTDataProvider(null, sub);
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "");
        if (name != null) {
            throw new IllegalStateException("Previous name not handled: " + name + " -> " + key);
        } else {
            CompoundBinaryTag var10000 = CompoundBinaryTag.empty();
            Objects.requireNonNull(var10000);
            obj = obj.put(key, var10000);
            return new NamedNBTDataProvider(null, var10000);
        }
    }

    public static @NotNull DataProvider createProvider$core(@NotNull BinaryTag tag) {
        Objects.requireNonNull(tag, "tag");
        return tag instanceof CompoundBinaryTag ? new NamedNBTDataProvider(null, (CompoundBinaryTag) tag) : new NBTDataProvider(tag);
    }
}
