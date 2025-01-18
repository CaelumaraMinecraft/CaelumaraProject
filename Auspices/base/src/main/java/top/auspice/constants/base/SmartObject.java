package top.auspice.constants.base;

import org.jetbrains.annotations.ApiStatus.Internal;

public interface SmartObject {
    @Internal
    void invalidateObject();

    @Internal
    boolean hasObjectExpired();

    @Internal
    default void ensureObjectExpiration() {
        if (this.hasObjectExpired()) {
            throw new IllegalStateException("This object instance has been unloaded from data but is being used: " + this);
        }
    }

    @Internal
    void saveObjectState(boolean var1);

    @Internal
    boolean isObjectStateSaved();

    @Internal
    boolean shouldSave();
}