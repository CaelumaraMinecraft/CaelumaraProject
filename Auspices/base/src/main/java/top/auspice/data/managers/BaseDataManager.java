package top.auspice.data.managers;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.key.NSKeyed;
import top.auspice.key.NSedKey;

import java.io.Closeable;
import java.time.Duration;
import java.util.Objects;

public abstract class BaseDataManager implements Closeable, NSKeyed {

    private final @NotNull NSedKey NSedKey;
    private final @Nullable Duration autoSaveInterval;
    private final boolean isCacheStatic;
    private final boolean isTemporary;
    private final boolean isSmartSaving;
    private boolean isClosed;
    private boolean shouldSaveData;

    public BaseDataManager(@NotNull NSedKey key, @Nullable Duration autoSaveInterval, boolean isCacheStatic, boolean isTemporary, boolean isSmartSaving) {
        Objects.requireNonNull(key, "key");
        this.NSedKey = key;
        this.autoSaveInterval = autoSaveInterval;
        this.isCacheStatic = isCacheStatic;
        this.isTemporary = isTemporary;
        this.isSmartSaving = isSmartSaving;
        this.shouldSaveData = true;
    }

    public final @Nullable Duration getAutoSaveInterval() {
        return this.autoSaveInterval;
    }

    public final boolean isCacheStatic() {
        return this.isCacheStatic;
    }

    public final boolean isTemporary() {
        return this.isTemporary;
    }

    public final boolean isSmartSaving() {
        return this.isSmartSaving;
    }

    public final boolean isClosed() {
        return this.isClosed;
    }

    public final boolean getShouldSaveData() {
        return this.shouldSaveData;
    }

    public final void setShouldSaveData(boolean shouldSaveData) {
        this.shouldSaveData = shouldSaveData;
    }

    public abstract void onDisable();

    public final @NotNull NSedKey getNamespacedKey() {
        return this.NSedKey;
    }

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
