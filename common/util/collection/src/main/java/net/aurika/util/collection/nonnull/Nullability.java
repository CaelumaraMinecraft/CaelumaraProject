package net.aurika.util.collection.nonnull;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public final class Nullability {

  @NotNull
  public static <E> Collection<E> assertNonNullElements(@Nullable Collection<E> $this$assertNonNullElements) {
    if ($this$assertNonNullElements == null) {
      throw new IllegalArgumentException();
    } else {
      for (Object t : $this$assertNonNullElements) {
        if (t == null) {
          throw new IllegalArgumentException($this$assertNonNullElements.getClass().getSimpleName() + " contains null");
        }
      }

      return $this$assertNonNullElements;
    }
  }

  public static <E> E assertNonNull(@NotNull Collection<? extends E> $this$assertNonNull, @Nullable E obj) {
    Validate.Arg.notNull($this$assertNonNull, "$this$assertNonNull");
    if (obj == null) {
      throw new IllegalArgumentException(
          $this$assertNonNull.getClass().getSimpleName() + " cannot contain null values");
    } else {
      return obj;
    }
  }

}
