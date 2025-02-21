package net.aurika.common.key.registry;

import net.aurika.common.key.Ident;
import net.aurika.common.key.Identified;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractIdentifiedRegistry<T extends Identified> implements IdentifiedRegistry<T> {
    private Map<Ident, T> registry;

    protected AbstractIdentifiedRegistry() {
    }

    protected AbstractIdentifiedRegistry(Map<Ident, T> registry) {
        this.registry = registry;
    }

    /**
     * Get the raw registry.
     *
     * @return The raw registry
     */
    protected Map<Ident, T> rawRegistry() {
        return this.registry;
    }

    /**
     * Set the raw registry.
     *
     * @return The old registry (maybe null)
     */
    protected Map<Ident, T> rawRegistry(Map<Ident, T> newRegistry) {
        var oldRegistry = registry;
        this.registry = newRegistry;
        return oldRegistry;
    }

    @Override
    public void register(T obj) {
        if (obj == null) return;
        if (this.registry == null) {
            this.registry = new HashMap<>();
        }
        Ident key = obj.ident();
        Objects.requireNonNull(key, "Obj key");
        this.registry.put(key, obj);
    }

    @Override
    public boolean isRegistered(Ident key) {
        return this.registry != null && this.registry.containsKey(key);
    }

    @Override
    public @Nullable T getRegistered(Ident ident) {
        return this.registry == null ? null : this.registry.get(ident);
    }
}
