package net.aurika.util.cache.single;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

public class ExpirableCachedSupplier<T> extends CachedSupplier<T> {

    private final@NotNull Duration cacheTime;
    private long lastChecked;

    public ExpirableCachedSupplier(@NotNull Supplier<T> getter, @NotNull Duration cacheTime) {
        super(getter);
        this.cacheTime = Objects.requireNonNull(cacheTime);
        this.lastChecked = System.currentTimeMillis();
        if (this.cacheTime.getSeconds() <= 5L) {
            throw new IllegalArgumentException("Any cache time under 5 seconds is not likely to help with performance: " + this.cacheTime.toMillis() + "ms");
        }
    }

    public T get() {
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - this.lastChecked;
        if (this.cached == null || this.cacheTime.minusMillis(diff).isNegative()) {
            this.cached = this.supplier.get();
            this.lastChecked = currentTime;
        }

        T cached = this.cached;
        Objects.requireNonNull(cached, "cached");
        return cached;
    }
}
