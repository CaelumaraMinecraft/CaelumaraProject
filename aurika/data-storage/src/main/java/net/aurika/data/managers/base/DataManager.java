package net.aurika.data.managers.base;

import net.aurika.data.database.base.Database;
import net.aurika.data.managers.BaseDataManager;
import net.aurika.data.api.DataObject;
import net.aurika.namespace.NSedKey;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.util.Collection;

public abstract class DataManager<T extends DataObject> extends BaseDataManager {
    private final NSedKey id;
    protected boolean savingState = true;

    public DataManager(NSedKey id, BaseDataManager inherited) {
        super(id, inherited.getAutoSaveInterval(), inherited.isCacheStatic(), inherited.isTemporary(), inherited.isSmartSaving());
        this.id = id;
    }

    protected void saveObjectState(T object, boolean var2) {
        if (object != null && this.isSmartSaving() && !object.isObjectStateSaved()) {
            object.saveObjectState(var2);
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
    protected T onLoad(T obj) {
        return obj;
    }

    public void onDisable() {
    }

    @MustBeInvokedByOverriders
    protected void unload(@NonNull T object) {
        object.invalidateObject();
    }

    public abstract int size();

    public abstract Collection<T> loadAllData(boolean var1);

    public abstract Collection<T> getLoadedData();

    public abstract Collection<T> peekAllData();

    public String toString() {
        return this.getClass().getSimpleName() + '(' + this.id.asString() + " | " + this.size() + ')';
    }

    @Internal
    public void close() {
        super.close();
        this.getDatabase().close();
    }
}
