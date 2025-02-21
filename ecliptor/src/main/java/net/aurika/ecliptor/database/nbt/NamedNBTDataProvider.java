package net.aurika.ecliptor.database.nbt;

import net.aurika.common.function.FloatSupplier;
import net.aurika.common.function.TriConsumer;
import net.aurika.common.nbt.*;
import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.util.unsafe.fn.Fn;
import net.aurika.util.uuid.FastUUID;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;

public class NamedNBTDataProvider implements DataProvider, SectionCreatableDataSetter {

    private @Nullable String name;
    private @NotNull NBTTagCompound obj;

    public NamedNBTDataProvider(@Nullable String name, @NotNull NBTTagCompound obj) {
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

    public @NotNull NBTTagCompound obj() {
        return obj;
    }

    public void obj(@NotNull NBTTagCompound obj) {
        Objects.requireNonNull(obj, "obj");
        this.obj = obj;
    }

    private NBTTagCompound _compound() {
        return name == null ? obj : (NBTTagCompound) obj.get(name);
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
        NBTTagCompound var10003 = _compound();
        if (var10003 == null) {
            var10003 = NBTTagCompound.empty();
        }

        return new NamedNBTDataProvider(null, var10003);
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        NBTTag tag = obj.get(_name());
        return tag != null ? ((NBTTagInt) tag).value() : def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def, "def");
        NBTTag tag = obj.get(_name());
        return tag != null ? ((NBTTagLong) tag).value() : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "def");
        NBTTag tag = obj.get(_name());
        return tag != null ? ((NBTTagFloat) tag).value() : def.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "def");
        NBTTag tag = obj.get(_name());
        return tag != null ? ((NBTTagDouble) tag).value() : def.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "def");
        NBTTag tag = obj.get(_name());
        return tag != null ? ((NBTTagByte) tag).booleanValue() : def.getAsBoolean();
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "def");
        NBTTag tag = obj.get(_name());
        return tag != null ? ((NBTTagString) tag).value() : def.get();
    }

    @Override
    public @Nullable UUID asUUID() {
        String s = asString();
        return s != null ? FastUUID.fromString(s) : null;
    }

    @Override
    public <T extends StructuredDataObject> T asStruct(@NotNull DataStructSchema<T> template) {
        Validate.Arg.notNull(template, "template");
        String s = asString();
        return s != null ? template.plainToObject(s) : null;
    }

    @Override
    public <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(handler, "dataProcessor");
        NBTTagList list = (NBTTagList) obj.get(_name());
        if (list != null) {
            List<NBTTag> elements = list.rawValue();
            for (NBTTag eTag : elements) {
                handler.accept(c, createProvider$core(eTag));
            }
        }
        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Objects.requireNonNull(m, "m");
        Objects.requireNonNull(handler, "dataProcessor");
        NBTTagCompound compound = _compound();
        if (compound != null) {
            for (Map.Entry<String, ? extends NBTTag> entry : compound.rawValue().entrySet()) {
                handler.accept(m, new NBTDataProvider(NBTTagString.nbtTagString(entry.getKey())), createProvider$core(entry.getValue()));
            }
        }
        return m;
    }

    @Override
    public void setInt(int value) {
        obj.put(_name(), NBTTagInt.nbtTagInt(value));
    }

    @Override
    public void setLong(long value) {
        obj.put(_name(), NBTTagLong.nbtTagLong(value));
    }

    @Override
    public void setFloat(float value) {
        obj.put(_name(), NBTTagFloat.nbtTagFloat(value));
    }

    @Override
    public void setDouble(double value) {
        obj.put(_name(), NBTTagDouble.nbtTagDouble(value));
    }

    @Override
    public void setBoolean(boolean value) {
        obj.put(_name(), NBTTagByte.nbtTagBool(value));
    }

    @Override
    public void setString(@Nullable String value) {
        if (value != null) obj.put(_name(), NBTTagString.nbtTagString(value));
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) obj.put(_name(), NBTTagString.nbtTagString(FastUUID.toString(value)));
    }

    @Override
    public void setStruct(@Nullable StructuredDataObject value) {
        if (value != null) setString(value.dataStructSchema().objectToPlain(Fn.cast(value)));
    }

    @Override
    public <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "");
        if (!value.isEmpty()) {
            NBTTagList var10000 = NBTTagList.unknownEmpty();

            for (E var4 : value) {
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
            final NBTTagCompound var3 = NBTTagCompound.empty();

            for (Map.Entry<K, ? extends V> entry : value.entrySet()) {
                handler.map(
                        entry.getKey(),
                        new StringMappedIdSetter((s) -> new NamedNBTDataProvider(s, var3)),
                        entry.getValue()
                );
            }

            obj.put(_name(), var3);
        }
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        NBTTagCompound sub = NBTTagCompound.empty();
        String name = this.name;
        if (name == null) throw new IllegalStateException("No key name set");
        obj.put(name, sub);
        return new NamedNBTDataProvider(null, sub);
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "");
        if (name != null) {
            throw new IllegalStateException("Previous name not handled: " + name + " -> " + key);
        } else {
            NBTTagCompound var10000 = NBTTagCompound.empty();
            Objects.requireNonNull(var10000);
            obj.put(key, var10000);
            return new NamedNBTDataProvider(null, var10000);
        }
    }

    public static @NotNull DataProvider createProvider$core(@NotNull NBTTag tag) {
        Objects.requireNonNull(tag, "tag");
        return tag instanceof NBTTagCompound ? new NamedNBTDataProvider(null, (NBTTagCompound) tag) : new NBTDataProvider(tag);
    }
}
