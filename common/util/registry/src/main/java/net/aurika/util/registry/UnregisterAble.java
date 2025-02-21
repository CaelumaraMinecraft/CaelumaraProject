package net.aurika.util.registry;

public interface UnregisterAble<K, O> extends Registry<K, O> {
    O unregister(K key);
}
