package top.auspice.data.managers.base;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import top.auspice.constants.base.AuspiceObject;
import top.auspice.key.NSedKey;
import top.auspice.data.database.base.KingdomsDatabase;
import top.auspice.data.managers.BaseDataManager;

import java.util.Collection;

public abstract class DataManager<T extends AuspiceObject> extends BaseDataManager {
    private final NSedKey NSedKey;
    protected boolean savingState = true;

    public DataManager(NSedKey NSedKey, BaseDataManager var2) {
        super(NSedKey, var2.getAutoSaveInterval(), var2.isCacheStatic(), var2.isTemporary(), var2.isSmartSaving());
        this.NSedKey = NSedKey;
    }

    protected void saveObjectState(T object, boolean var2) {
        if (object != null && this.isSmartSaving() && !object.isObjectStateSaved()) {
            object.saveObjectState(var2);
        }

    }

    @Internal
    public abstract KingdomsDatabase<T> getDatabase();

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
    protected T onLoad(T var1) {
        return var1;
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
        return this.getClass().getSimpleName() + '(' + this.NSedKey.asString() + " | " + this.size() + ')';
    }

    @Internal
    public void close() {
        super.close();
        this.getDatabase().close();
    }
}
