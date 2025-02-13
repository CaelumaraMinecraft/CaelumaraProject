package net.aurika.ecliptor.managers.base;

import net.aurika.ecliptor.centers.AurikaDataCenter;
import net.aurika.ecliptor.database.base.SingularDatabase;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.namespace.NSedKey;
import top.auspice.utils.logging.AuspiceLogger;

import java.util.Collection;
import java.util.Collections;

public abstract class SingularDataManager<T extends DataObject.Impl> extends DataManager<T> {
    private T a;
    private final SingularDatabase<T> b;

    public SingularDataManager(NSedKey var1, SingularDatabase<T> var2, AurikaDataCenter var3) {
        super(var1, var3);
        this.b = var2;
    }

    public SingularDatabase<T> getDatabase() {
        return this.b;
    }

    public void deleteAllData() {
        if (this.a != null) {
            this.a.invalidateObject();
        }

        this.b.deleteAllData();
    }

    public void copyAllDataTo(DataManager<T> var1) {
        if (!(var1 instanceof SingularDataManager<T> singularDataManager)) {
            throw new UnsupportedOperationException("Cannot copy from " + this + " to " + var1);
        } else {
            singularDataManager.a = this.a;
        }
    }

    public T getOrLoadData() {
        if (this.a != null) {
            return this.a;
        } else {
            this.a = this.b.load();
            this.a = this.onLoad(this.a);
            return this.a;
        }
    }

    public void setCached(T var1) {
        if (this.a != var1) {
            this.clearCache();
            this.a = var1;
            this.saveObjectState(var1, true);
        }
    }

    public void clearCache() {
        if (this.a != null) {
            this.a.invalidateObject();
            this.a = null;
        }
    }

    public int saveAll(boolean var1) {
        if (this.a == null) {
            return 0;
        } else {
            if (var1) {
                if (!super.savingState) {
                    AuspiceLogger.info("Saving state was turned off for " + this + ", skipping saving data...");
                    return 0;
                }

                if (this.isSmartSaving() && !this.a.shouldSave()) {
                    return 0;
                }

                this.b.save(this.a);
            } else {
                this.b.save(this.a);
            }

            return 1;
        }
    }

    public int size() {
        return 1;
    }

    public Collection<T> loadAllData(boolean var1) {
        return this.peekAllData();
    }

    public Collection<T> getLoadedData() {
        return this.a == null ? Collections.emptyList() : Collections.singletonList(this.a);
    }

    public Collection<T> peekAllData() {
        return Collections.singletonList(this.getOrLoadData());
    }
}
