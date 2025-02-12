package net.aurika.util.registry;

public interface Lockable {
    void lock();

    boolean isLocked();
}
