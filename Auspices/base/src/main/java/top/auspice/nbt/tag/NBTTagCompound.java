package top.auspice.nbt.tag;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.NBTTagId;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.NBTStreamable;
import top.auspice.nbt.stream.internal.FlatteningNBTStream;
import top.auspice.nbt.stream.internal.SurroundingNBTStream;
import top.auspice.nbt.stream.token.NBTToken;

import java.io.IOException;
import java.util.*;

public class NBTTagCompound extends NBTTag<Map<String, ? extends NBTTag<?>>> {
    private Map<String, ? extends NBTTag<?>> value;

    @NotNull
    public static NBTTagCompound of(@NotNull Map<String, ? extends NBTTag<?>> value) {
        return new NBTTagCompound(value, true);
    }

    @NotNull
    public static NBTTagCompound empty() {
        return new NBTTagCompound(new LinkedHashMap(), false);
    }

    public NBTTagCompound(@NotNull Map<String, ? extends NBTTag<?>> value, boolean check) {
        if (check) {
            for (NBTTag<?> tag : value.values()) {
                if (tag.type().id() == NBTTagId.END) {
                    throw new IllegalArgumentException("Cannot add END tag to compound tag");
                }
            }
        }

        this.value = Objects.requireNonNull(value, "value is null");
    }

    public <T> PropertyRef<T> createReference(String name, NBTTag<T> tag, boolean set) {
        PropertyRef<T> ref = new PropertyRef<T>(name, tag);
        if (set) {
            ref.set(tag.value());
        }

        return ref;
    }

    @Contract("_, _ -> this")
    @NotNull
    public <T extends NBTTag<?>> NBTTagCompound set(@NotNull String name, T value) {
        if (value.type().id() == NBTTagId.END) {
            throw new IllegalArgumentException("Cannot add END tag to compound tag");
        } else {
            this.value.put(name, NBTTagType.castAs(value));
            return this;
        }
    }

    @Contract("_ -> this")
    @NotNull
    public NBTTagCompound putAll(@NotNull Map<String, ? extends NBTTag<?>> map) {
        map.forEach(this::set);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public NBTTagCompound remove(@NotNull String name) {
        this.value.remove(name);
        return this;
    }

    public boolean has(@NotNull String name) {
        return this.value.containsKey(name);
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, byte[] value) {
        return this.set(name, NBTTagByteArray.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, byte value) {
        return this.set(name, NBTTagByte.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, double value) {
        return this.set(name, NBTTagDouble.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, float value) {
        return this.set(name, NBTTagFloat.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, int[] value) {
        return this.set(name, NBTTagIntArray.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, int value) {
        return this.set(name, NBTTagInt.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, boolean value) {
        return this.set(name, NBTTagBool.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, long[] value) {
        return this.set(name, NBTTagLongArray.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, long value) {
        return this.set(name, NBTTagLong.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, short value) {
        return this.set(name, NBTTagShort.of(value));
    }

    @Contract("_, _ -> this")
    @NotNull
    public NBTTagCompound set(@NotNull String name, String value) {
        return this.set(name, NBTTagString.of(value));
    }

    @NotNull
    public static NBTTagCompound readFrom(@NotNull NBTStream tokens) throws IOException {
        return NBTTagReader.readCompound(tokens);
    }

    @NotNull
    public NBTTagType<NBTTagCompound> type() {
        return NBTTagType.COMPOUND;
    }

    @NotNull
    public Map<String, ? extends NBTTag<?>> value() {
        return this.value;
    }

    public void setValue(Map<String, ? extends NBTTag<?>> value) {
        this.value = value;
    }

    @NotNull
    public NBTStream stream() {
        return new SurroundingNBTStream(new NBTToken.CompoundStart(), new FlatteningNBTStream(new EntryTokenIterator()), new NBTToken.CompoundEnd());
    }

    @Nullable
    public <T extends NBTTag<?>> T tryGetTag(@NotNull String name, NBTTagType<T> type) {
        NBTTag<?> tag = this.value.get(name);
        return tag != null && type == tag.type() ? type.cast(tag) : null;
    }

    @Nullable
    public <T extends NBTTag<?>> NBTTagList<T> tryGetListTag(@NotNull String name, NBTTagType<T> elementType) {
        NBTTagList<T> listTag = this.tryGetTag(name, NBTTagType.listOf());
        return listTag != null && listTag.elementType() == elementType ? listTag : null;
    }

    private <T> T get(String name) {
        NBTTag<?> tag = this.value.get(name);
        if (tag == null) {
            return null;
        } else {
            try {
                return (T) tag.value();
            } catch (ClassCastException var4) {
                return null;
            }
        }
    }

    public NBTTagCompound getCompound(String name) {
        return this.tryGetTag(name, NBTTagType.COMPOUND);
    }

    public String getString(String name) {
        return this.get(name);
    }

    public <T, Tag extends NBTTag<T>> T get(String name, NBTTagType<Tag> type) {
        NBTTag<T> tag = this.tryGetTag(name, type);
        return tag == null ? null : tag.value();
    }

    @NotNull
    public <T extends NBTTag<?>> T getTag(@NotNull String name, NBTTagType<T> type) {
        NBTTag<?> tag = this.value.get(name);
        if (tag == null) {
            throw new NoSuchElementException("No tag under the name '" + name + "' exists");
        } else if (type != tag.type()) {
            throw new IllegalStateException("Tag under '" + name + "' exists, but is a " + tag.type().name() + " instead of " + type.name());
        } else {
            return type.cast(tag);
        }
    }

    @NotNull
    public <T extends NBTTag<?>> NBTTagList<T> getListTag(@NotNull String name, NBTTagType<T> elementType) {
        NBTTagList<T> listTag = this.getTag(name, NBTTagType.listOf());
        if (listTag.elementType() != elementType) {
            throw new IllegalStateException("Tag under '" + name + "' exists, but is a " + listTag.elementType().name() + " list instead of a " + elementType.name() + " list");
        } else {
            return listTag;
        }
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + this.value;
    }

    public class PropertyRef<T> {
        private final String name;
        private final NBTTag<T> tag;
        private final T defaultValue;
        private boolean removed;

        private PropertyRef(String name, NBTTag<T> tag) {
            this.removed = true;
            this.name = name;
            this.tag = tag;
            this.defaultValue = tag.value();
        }

        private void ensureState() {
            NBTTag<?> currentTag = NBTTagCompound.this.tryGetTag(this.name, this.tag.type());
            if (this.tag != currentTag) {
                throw new ConcurrentModificationException("Tag '" + this.name + "' was changed unexpectedly: " + this.tag.value() + " -> " + currentTag);
            }
        }

        public void set(@Nullable T value) {
            if (value == null) {
                this.remove();
            } else {
                this.tag.setValue(value);
                if (this.removed) {
                    NBTTagCompound.this.set(this.name, this.tag);
                    this.removed = false;
                }
            }

        }

        public T get() {
            return this.removed ? this.defaultValue : this.tag.value();
        }

        public boolean isSet() {
            return !this.removed;
        }

        public void remove() {
            NBTTagCompound.this.remove(this.name);
            this.removed = true;
        }
    }

    private class EntryTokenIterator implements Iterator<NBTStreamable> {
        private final Iterator<? extends Map.Entry<String, ? extends NBTTag<?>>> entryIterator;

        private EntryTokenIterator() {
            this.entryIterator = NBTTagCompound.this.value.entrySet().iterator();
        }

        public boolean hasNext() {
            return this.entryIterator.hasNext();
        }

        public NBTStreamable next() {
            Map.Entry<String, ? extends NBTTag<?>> entry = this.entryIterator.next();
            return new SurroundingNBTStream(new NBTToken.Name(entry.getKey(), Optional.of(((NBTTag) entry.getValue()).type().id())), entry.getValue().stream(), null);
        }
    }
}
