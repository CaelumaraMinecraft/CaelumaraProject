package top.auspice.data.centers;

import org.jetbrains.annotations.ApiStatus.Internal;
import top.auspice.configs.texts.MessageHandler;
import top.auspice.data.object.DataObject;
import top.auspice.data.object.KeyedDataObject;
import top.auspice.key.NSedKey;
import top.auspice.key.NamespacedMap;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.base.KeyedDatabase;
import top.auspice.data.database.base.Database;
import top.auspice.data.database.base.SingularDatabase;
import top.auspice.data.handlers.abstraction.DataHandler;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;
import top.auspice.data.handlers.abstraction.SingularDataHandler;
import top.auspice.data.managers.BaseDataManager;
import top.auspice.data.managers.base.DataManager;
import top.auspice.api.user.AuspiceUser;
import top.auspice.scheduler.DelayedRepeatingTask;
import top.auspice.utils.debug.AuspiceDebug;
import top.auspice.utils.unsafe.string.StringPadder;
import top.auspice.utils.logging.AuspiceLogger;
import top.auspice.utils.string.Strings;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public abstract class DataCenter extends BaseDataManager {
    protected final DatabaseType databaseType;
    protected long lastSignalledSave;
    protected final NamespacedMap<DataManager<?>> registry = new NamespacedMap<>();
    protected boolean loadedInitials = false;
    protected final DelayedRepeatingTask autoSaveTask;

    @Internal
    public DataCenter(NSedKey NSedKey, DatabaseType databaseType, Duration autoSaveInterval, boolean isCacheStatic, boolean isTemporary, boolean isSmartSaving) {
        super(NSedKey, autoSaveInterval, isCacheStatic, isTemporary, isSmartSaving);
        this.databaseType = Objects.requireNonNull(databaseType);
        if (autoSaveInterval != null) {
            this.autoSaveTask = AuspiceUser.taskScheduler().async().repeating(autoSaveInterval, autoSaveInterval, new a());
        } else {
            this.autoSaveTask = null;
        }
    }

    @Internal
    public boolean hasLoadedInitials() {
        return this.loadedInitials;
    }

    public <T extends DataManager<?>> T register(T dataManager) {
        this.registry.put(dataManager.getNamespacedKey(), dataManager);
        return dataManager;
    }

    @Internal
    public void onDisable() {
        this.getAllDataManagers().forEach(DataManager::onDisable);
    }

    public void saveAll(SaveType var1) {
        AuspiceLogger.debug(AuspiceDebug.SAVE_ALL, "Saving all data...");
        ArrayList<Throwable> errs = new ArrayList<>();
        StringPadder var3 = new StringPadder();

        for (DataManager<?> dataManager : this.getAllDataManagers().stream().sorted(Comparator.comparing((var0) -> var0.getNamespacedKey().asString())).toList()) {
            int var6 = dataManager.size();
            long var8 = System.currentTimeMillis();
            int var7 = 0;
            Throwable var10 = null;

            try {
                if (var1 == SaveType.AUTO) {
                    var7 = dataManager.autoSave();
                } else {
                    var7 = dataManager.saveAll(dataManager.isSmartSaving());
                }
            } catch (Throwable var16) {
                var10 = var16;
                errs.add(var16);
            }

            long var14 = System.currentTimeMillis() - var8;
            var3.pad("\n  &7| &3" + dataManager.getNamespacedKey().asString() + "&8: &9", (var10 == null ? var7 : "&cFailed") + "&7/&9" + var6, " &8(&9" + var14 + "ms&8)");
        }

        MessageHandler.sendConsolePluginMessage("&2" + var1.description + " Save&8: " + var3.getPaddedString(""));
        errs.forEach(Throwable::printStackTrace);
        if (var1 == DataCenter.SaveType.SIGNALLED) {
            this.lastSignalledSave = System.currentTimeMillis();
        }

    }

    public NamespacedMap<DataManager<?>> getRegistry() {
        return this.registry;
    }

    public <K, T extends KeyedDataObject.Impl<K>> KeyedDatabase<K, T> constructDatabase(String var1, String var2, KeyedDataHandler<K, T> var3) {
        return (KeyedDatabase<K, T>) this.constructDatabase0(var1, var2, var3);
    }

    public <T extends DataObject.Impl> SingularDatabase<T> constructDatabase(String var1, String var2, SingularDataHandler<T> var3) {
        return (SingularDatabase<T>) this.constructDatabase0(var1, var2, var3);
    }

    public DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    protected abstract <T extends DataObject.Impl> Database<T> constructDatabase0(String var1, String var2, DataHandler<T> dataHandler);

    public Collection<DataManager<?>> getAllDataManagers() {
        return this.registry.values();
    }

    public void signalFullSave() {
        Duration var1 = Duration.ofMillis(this.lastSignalledSave).plus(Duration.ofSeconds(30L));
        if (!Duration.ofMillis(System.currentTimeMillis()).minus(var1).isNegative()) {
            this.saveAll(DataCenter.SaveType.SIGNALLED);
        }

    }

    public long getLastSignalledSave() {
        return this.lastSignalledSave;
    }

    public void close() {
        super.close();
        if (this.autoSaveTask != null) {
            this.autoSaveTask.cancel();
        }

        this.getAllDataManagers().forEach(DataManager::close);
    }

    private final class a implements Runnable {
        private a() {
        }

        public void run() {
            DataCenter.this.saveAll(DataCenter.SaveType.AUTO);
        }
    }

    public enum SaveType {
        AUTO,
        SIGNALLED,
        MANUAL;

        final String description = Strings.capitalize(this.name());
    }
}
