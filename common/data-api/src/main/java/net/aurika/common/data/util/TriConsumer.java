package net.aurika.common.data.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, V> {

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @param v the third input argument
   */
  void accept(T t, U u, V v);

  default @NotNull TriConsumer<T, U, V> andThen(@NotNull TriConsumer<? super T, ? super U, ? super V> after) {
    Objects.requireNonNull(after);

    return (t, u, v) -> {
      this.accept(t, u, v);
      after.accept(t, u, v);
    };
  }

}