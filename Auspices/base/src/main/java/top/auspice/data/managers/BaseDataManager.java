package top.auspice.data.managers;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.key.NSedKey;
import top.auspice.key.NSKeyed;

import java.io.Closeable;
import java.time.Duration;
import java.util.Objects;

public abstract class BaseDataManager implements Closeable, NSKeyed {
    @NotNull
    private final NSedKey NSedKey;
    @Nullable
    private final Duration autoSaveInterval;
    private final boolean isCacheStatic;
    private final boolean isTemporary;
    private final boolean isSmartSaving;
    private boolean isClosed;
    private boolean shouldSaveData;

    public BaseDataManager(@NotNull NSedKey NSedKey, @Nullable Duration autoSaveInterval, boolean isCacheStatic, boolean isTemporary, boolean isSmartSaving) {
        Objects.requireNonNull(NSedKey);
        this.NSedKey = NSedKey;
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
