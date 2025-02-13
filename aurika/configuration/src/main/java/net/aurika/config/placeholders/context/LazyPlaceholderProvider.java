package net.aurika.config.placeholders.context;

import top.auspice.utils.cache.single.CacheableObject;
import top.auspice.utils.cache.single.CachedSupplier;

import java.util.function.Supplier;

public class LazyPlaceholderProvider implements PlaceholderProvider {
    private final CacheableObject<PlaceholderProvider> a;

    public LazyPlaceholderProvider(Supplier<PlaceholderProvider> placeholderProviderSupplier) {
        this.a = new CachedSupplier<>(placeholderProviderSupplier);
    }

    public Object providePlaceholder(String name) {
        return this.a.get().providePlaceholder(name);
    }

    public String toString() {
        return "LazyPlaceholderProvider{" + this.a.get().toString() + '}';
    }
}
