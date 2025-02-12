package net.aurika.utils.registry;

public interface Registry<K, O> {

    void register(O object);

    O getRegistered(K key);

    boolean isRegistered(K key);
}
