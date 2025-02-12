package net.aurika.data.database.nbt;

import net.aurika.data.database.dataprovider.*;
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

public class NBTDataProvider implements DataProvider, SectionCreatableDataSetter {

    private @NotNull BinaryTag element;

    public NBTDataProvider(@NotNull BinaryTag element) {
        Objects.requireNonNull(element, "element");
        this.element = element;
    }

    public @NotNull BinaryTag getElement$core() {
        return element;
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        CompoundBinaryTag sub = CompoundBinaryTag.empty();
        if (element instanceof CompoundBinaryTag) {
            element = ((CompoundBinaryTag) element).put(key, sub);
            return new NBTDataProvider(sub);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        CompoundBinaryTag e = CompoundBinaryTag.empty();
        if (element instanceof ListBinaryTag) {
            element = ((ListBinaryTag) element).add(e);
            return new NamedNBTDataProvider(null, e);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public @NotNull DataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        if (element instanceof CompoundBinaryTag) {
            return new NamedNBTDataProvider(key, (CompoundBinaryTag) element);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public @NotNull NBTDataProvider asSection() {
        return this;
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "def");
        return element instanceof StringBinaryTag ? ((StringBinaryTag) element).value() : def.get();
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
        return ((IntBinaryTag) element).value();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        return ((LongBinaryTag) element).value();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        return ((FloatBinaryTag) element).value();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "def");
        return ((DoubleBinaryTag) element).value();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "def");
        return ((ByteBinaryTag) element).value() != 0;
    }

    @Override
    public <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(dataProcessor, "dataProcessor");

        for (BinaryTag e : ((ListBinaryTag) element)) {
            dataProcessor.accept(c, new NBTDataProvider(e));
        }

        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(dataProcessor, "");

        for (Map.Entry<String, ? extends BinaryTag> entry : ((CompoundBinaryTag) element)) {
            dataProcessor.accept(m, new NBTDataProvider(StringBinaryTag.stringBinaryTag(entry.getKey())), new NBTDataProvider(entry.getValue()));
        }

        return m;
    }

    @Override
    public void setLocation(@Nullable SimpleLocation value) {
        if (value != null) {
            this.setString(value.asDataString());
        }
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
        if (element instanceof ListBinaryTag) {
            element = ((ListBinaryTag) element).add(LongBinaryTag.longBinaryTag(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setString(@Nullable String value) {
        if (value != null) {
            if (element instanceof ListBinaryTag) {
                element = ((ListBinaryTag) element).add(StringBinaryTag.stringBinaryTag(value));
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    @Override
    public void setInt(int value) {
        if (element instanceof ListBinaryTag) {
            element = ((ListBinaryTag) element).add(IntBinaryTag.intBinaryTag(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setFloat(float value) {
        if (element instanceof ListBinaryTag) {
            element = ((ListBinaryTag) element).add(FloatBinaryTag.floatBinaryTag(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setDouble(double value) {
        if (element instanceof ListBinaryTag) {
            element = ((ListBinaryTag) element).add(DoubleBinaryTag.doubleBinaryTag(value));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setBoolean(boolean value) {
        if (element instanceof ListBinaryTag) {
            element = ((ListBinaryTag) element).add(ByteBinaryTag.byteBinaryTag((byte) (value ? 1 : 0)));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUUID(@NotNull UUID value) {
        this.setString(FastUUID.toString(value));
    }
}
