package net.aurika.common.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface NBTTagArray<ARR> extends NBTTagObject<ARR> {
}

abstract class NBTTagArrayImpl<ARR> extends NBTTagImpl implements NBTTagArray<ARR> {
    protected NBTTagArrayImpl() {
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + Internal.arrayHashCode(this.rawValue());
        return result;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof NBTTagArray<?> that) {
            return Objects.deepEquals(this.rawValue(), that.rawValue());  // will invoke array equals
        } else {
            return false;
        }
    }

    public @NotNull String toString() {
        return this.getClass().getSimpleName() + Internal.arrayToString(this.valueAsObject());
    }
}
