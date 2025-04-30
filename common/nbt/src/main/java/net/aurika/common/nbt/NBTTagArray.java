package net.aurika.common.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface NBTTagArray<ARR> extends NBTTagMutableObject<ARR> {
}

abstract class NBTTagArrayImpl<ARR> extends NBTTagImpl implements NBTTagArray<ARR> {

  protected NBTTagArrayImpl() { }

  public int hashCode() {
    int result = 1;
    result = 31 * result + Internal.arrayHashCode(this.valueRaw());
    return result;
  }

  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof NBTTagArray<?>) {
      NBTTagArray<?> that = (NBTTagArray<?>) obj;
      return Objects.deepEquals(this.valueRaw(), that.valueRaw());
    } else {
      return false;
    }
  }

  public @NotNull String toString() {
    return this.getClass().getSimpleName() + Internal.arrayToString(this.valueAsObject());
  }

}
