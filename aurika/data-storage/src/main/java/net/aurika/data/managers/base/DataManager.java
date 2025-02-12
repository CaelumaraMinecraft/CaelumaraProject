package net.aurika.data.managers.base;

import net.aurika.data.api.DataObject;
import net.aurika.data.database.base.Database;
import net.aurika.data.managers.BaseDataManager;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract class DataManager<T extends DataObject> extends BaseDataManager {
    protected boolean savingState = true;

    public DataManager(Key id, @NotNull BaseDataManager inherited) {
        super(id, inherited.autoSaveInterval(), inherited.isCacheStatic(), inherited.isTemporary(), inherited.isSmartSaving());
    }

    protected void saveObjectState(T object, boolean var1) {
        if (object != null && this.isSmartSaving() && !object.isObjectStateSaved()) {
            object.saveObjectState(var1);
        }
    }

    @Internal
    public abstract Database<T> getDatabase();

    public abstract void deleteAllData();

    @Internal
    public final void setSavingState(boolean savingState) {
        this.savingState = savingState;
    }

    @Internal
    public abstract void copyAllDataTo(DataManager<T> var1);

    @Internal
    public abstract void clearCache();

    @Internal
    public final int autoSave() {
        return this.saveAll(this.isSmartSaving());
    }

    public abstract int saveAll(boolean var1);

    @MustBeInvokedByOverriders
    protected T onLoad(T data) {
        return data;
    }

    public void onDisable() {
    }

    @MustBeInvokedByOverriders
    protected void unload(@NotNull T data) {
        data.invalidateObject();
    }

    public abstract int size();

    public abstract Collection<T> loadAllData(boolean var1);

    public abstract Collection<T> getLoadedData();

    public abstract Collection<T> peekAllData();

    public String toString() {
        return this.getClass().getSimpleName() + '(' + id.asString() + " | " + size() + ')';
    }

    @Internal
    public void close() {
        super.close();
        this.getDatabase().close();
    }
}
