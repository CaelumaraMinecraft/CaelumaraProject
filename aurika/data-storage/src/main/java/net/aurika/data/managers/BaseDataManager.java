package net.aurika.data.managers;

import net.aurika.checker.Checker;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.time.Duration;

public abstract class BaseDataManager implements Closeable, Keyed {

    protected final @NotNull Key id;
    private final @Nullable Duration autoSaveInterval;
    private final boolean isCacheStatic;
    private final boolean isTemporary;
    private final boolean isSmartSaving;
    private boolean isClosed;
    private boolean shouldSaveData;

    public BaseDataManager(@NotNull Key id, @Nullable Duration autoSaveInterval, boolean isCacheStatic, boolean isTemporary, boolean isSmartSaving) {
        Checker.Arg.notNull(id, "id");
        this.id = id;
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
        return this.id;
    }

    /**
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
