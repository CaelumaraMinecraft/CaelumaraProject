package top.auspice.nbt.tag;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.internal.FlatteningNBTStream;
import top.auspice.nbt.stream.internal.SurroundingNBTStream;
import top.auspice.nbt.stream.token.NBTToken;

import java.util.*;

public class NBTTagList<T extends NBTTag<?>> extends NBTTag<List<T>> {
    @Nullable
    private NBTTagType<T> elementType;
    private List<T> value;

    @NotNull
    public static <T extends NBTTag<?>> NBTTagList<T> of(@NotNull NBTTagType<T> elementType, @NotNull List<T> value) {
        Objects.requireNonNull(elementType);
        Objects.requireNonNull(value);

        for (T t : value) {
            if (t.type() != elementType) {
                throw new IllegalArgumentException("Element is not of type " + elementType.name() + " but " + t.type().name());
            }
        }

        return new NBTTagList<T>(elementType, new ArrayList<>(value));
    }

    @NotNull
    public static NBTTagList<?> ofUnknown(@NotNull List<? extends NBTTag<?>> value) {
        NBTTagType<?> elementType = null;

        for (NBTTag<?> t : value) {
            if (elementType == null) {
                elementType = t.type();
            } else if (t.type() != elementType) {
                throw new IllegalArgumentException("Element is not of type " + elementType.name() + " but " + t.type().name());
            }
        }

        return new NBTTagList<>(elementType, NBTTagType.castAs(new ArrayList<>(value)));
    }

    @NotNull
    public static <T extends NBTTag<?>> NBTTagList<T> empty(@NotNull NBTTagType<T> elementType) {
        return new NBTTagList<>(elementType, new ArrayList<>());
    }

    @NotNull
    public static NBTTagList<?> unknownEmpty() {
        return new NBTTagList<>(null, new ArrayList<>());
    }

    @Contract("_ -> this")
    @NotNull
    public NBTTagList<T> add(T tag) {
        if (this.elementType != null) {
            if (tag.type() != this.elementType) {
                throw new IllegalArgumentException("Element is not of type " + this.elementType.name() + " but " + tag.type().name());
            }
        } else {
            this.elementType = (NBTTagType<T>) tag.type();
        }

        this.value.add(tag);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public NBTTagList<T> addUnknown(NBTTag<?> tag) {
        return this.add((T) tag);
    }

    @Contract("_ -> this")
    @NotNull
    public NBTTagList<T> addAll(@NotNull Collection<? extends T> tags) {
        tags.forEach(this::add);
        return this;
    }

    private NBTTagList(NBTTagType<T> elementType, List<T> value) {
        Objects.requireNonNull(value, "value is null");
        if (!value.isEmpty() && elementType == NBTTagType.END) {
            throw new IllegalArgumentException("A non-empty list cannot be of type END");
        } else {
            this.elementType = elementType;
            this.value = value;
        }
    }

    @NotNull
    public NBTTagType<NBTTagList<T>> type() {
        return NBTTagType.listOf();
    }

    @NotNull
    public NBTTagType<T> elementType() {
        return this.elementType;
    }

    public <U extends NBTTag<?>> NBTTagList<U> asTypeChecked(@NotNull NBTTagType<U> elementType) {
        if (elementType != this.elementType) {
            throw new IllegalStateException("List is of type " + this.elementType.name() + ", not " + elementType.name());
        } else {
            return (NBTTagList<U>) this;
        }
    }

    @NotNull
    public List<T> value() {
        return this.value;
    }

    public void setValue(List<T> value) {
        if (!value.isEmpty() && ((NBTTag) value.get(0)).type() != this.elementType) {
            throw new IllegalArgumentException("Type mismatch, expected " + this.elementType + " but got: " + ((NBTTag) value.get(0)).type());
        } else {
            this.value = value;
        }
    }

    @NotNull
    public NBTStream stream() {
        return new SurroundingNBTStream(new NBTToken.ListStart(OptionalInt.of(this.value.size()), Optional.of(this.elementType.id())), new FlatteningNBTStream(this.value.iterator()), new NBTToken.ListEnd());
    }

    public T get(int index) {
        return this.value.get(index);
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + this.value;
    }
}
