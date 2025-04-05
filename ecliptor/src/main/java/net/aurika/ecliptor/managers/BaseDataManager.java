package net.aurika.ecliptor.managers;

import net.aurika.common.key.Key;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.time.Duration;

public abstract class BaseDataManager implements Closeable, Keyed {

    protected final @NotNull Key key;
    private final @Nullable Duration autoSaveInterval;
    private final boolean isCacheStatic;
    private final boolean isTemporary;
    private final boolean isSmartSaving;
    private boolean isClosed;
    private boolean shouldSaveData;

    public BaseDataManager(@NotNull Key key, @Nullable Duration autoSaveInterval, boolean isCacheStatic, boolean isTemporary, boolean isSmartSaving) {
        Validate.Arg.notNull(key, "key");
        this.key = key;
        this.autoSaveInterval = autoSaveInterval;
        this.isCacheStatic = isCacheStatic;
        this.isTemporary = isTemporary;
        this.isSmartSaving = isSmartSaving;
        this.shouldSaveData = true;
    }

    public final @Nullable Duration autoSaveInterval() {
        return autoSaveInterval;
    }

    public final boolean isCacheStatic() {
        return isCacheStatic;
    }

    public final boolean isTemporary() {
        return isTemporary;
    }

    public final boolean isSmartSaving() {
        return isSmartSaving;
    }

    public final boolean isClosed() {
        return isClosed;
    }

    public final boolean shouldSaveData() {
        return shouldSaveData;
    }

    public final void shouldSaveData(boolean shouldSaveData) {
        this.shouldSaveData = shouldSaveData;
    }

    public abstract void onDisable();

    public final @NotNull Key key() {
        return this.key;
    }

    /**
     * Ensures this data manager is opened.
     * @throws IllegalArgumentException 当该 {@link BaseDataManager} 已经关闭时
     */
    protected final void ensureOpen() {
        if (this.isClosed) {
            throw new IllegalArgumentException(this + " is closed");
        }
    }

    @MustBeInvokedByOverriders
    public void close() {
        if (this.isClosed) {
            throw new IllegalArgumentException(this + " is already closed");
        } else {
            this.isClosed = true;
        }
    }
}
