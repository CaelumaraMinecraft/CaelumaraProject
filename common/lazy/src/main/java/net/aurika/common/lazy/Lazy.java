package net.aurika.common.lazy;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class Lazy<T> {

  public static <T> @NotNull Lazy<T> lazy(@NotNull Supplier<T> initializer) {
    Objects.requireNonNull(initializer, "initializer");
    return new Lazy<T>() {
      @Override
      protected T init() {
        return initializer.get();
      }
    };
  }

  private T value;
  private boolean initialized;

  public Lazy() { }

  protected abstract T init();

  public T get() {
    if (!initialized) {
      value = init();
      initialized = true;
    }
    return value;
  }

  public boolean initialized() {
    return initialized;
  }

}
