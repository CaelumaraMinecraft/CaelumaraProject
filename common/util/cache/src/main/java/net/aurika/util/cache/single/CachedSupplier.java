package net.aurika.util.cache.single;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public class CachedSupplier<T> implements CacheableObject<T> {

  protected final Supplier<T> supplier;
  protected T cached;
  protected Boolean present;

  public CachedSupplier(@NotNull Supplier<T> supplier) {
    Objects.requireNonNull(supplier, "supplier");
    this.supplier = supplier;
  }

  public static <T> @NotNull CachedSupplier<T> of(@NotNull Supplier<T> supplier) {
    return supplier instanceof CachedSupplier ? (CachedSupplier<T>) supplier : new CachedSupplier<>(supplier);
  }

  public void invalidate() {
    this.cached = null;
  }

  public boolean isCached() {
    return this.present;
  }

  public T get() {
    if (this.cached == null) {
      this.cached = this.supplier.get();
      this.present = this.cached != null;
    }

    return this.cached;
  }

  public void set(@Nullable T cache) {
    this.cached = cache;
  }

}