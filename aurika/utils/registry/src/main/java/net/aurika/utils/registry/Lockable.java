package net.aurika.utils.registry;

public interface Lockable {
    void lock();

    boolean isLocked();
}
