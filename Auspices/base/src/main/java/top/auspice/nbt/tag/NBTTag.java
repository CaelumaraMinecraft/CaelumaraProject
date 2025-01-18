package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;
import top.auspice.nbt.stream.NBTStreamable;

import java.util.Objects;

public abstract class NBTTag<T> implements ToNBTTag<NBTTag<T>>, NBTStreamable {
    protected NBTTag() {
    }

    public abstract @NotNull NBTTagType<? extends NBTTag<T>> type();

    public abstract T value();

    public abstract void setValue(T var1);

    @NotNull
    public final NBTTag<T> toNBTTag() {
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            NBTTag<?> that = (NBTTag<?>) o;
            return Objects.equals(this.value(), that.value());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.value().hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.value() + ']';
    }
}
