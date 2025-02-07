package net.aurika.abstraction;

import java.util.HashMap;
import java.util.Map;

public interface Registry<K, O> {

    void register(O object);

    O getRegistered(K key);

    boolean isRegistered(K key);

    abstract class Impl<K, O> implements Registry<K, O> {
        private final Map<K, O> registry;

        protected Impl() {
            this(new HashMap<>());
        }

        protected Impl(Map<K, O> registry) {
            this.registry = registry;
        }

        protected abstract K getKey(O o);

        protected Map<K, O> getRawRegistry() {
            return registry;
        }

        @Override
        public void register(O object) {
            registry.put(getKey(object), object);
        }

        @Override
        public O getRegistered(K key) {
            return registry.get(key);
        }
    }
}
