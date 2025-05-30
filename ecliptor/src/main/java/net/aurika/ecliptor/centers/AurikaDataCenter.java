package net.aurika.ecliptor.centers;

import net.aurika.common.ident.Ident;
import net.aurika.common.key.namespace.NSKeyMap;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.ecliptor.api.KeyedDataObject;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.base.Database;
import net.aurika.ecliptor.database.base.KeyedDatabase;
import net.aurika.ecliptor.database.base.SingularDatabase;
import net.aurika.ecliptor.handler.DataHandler;
import net.aurika.ecliptor.handler.KeyedDataHandler;
import net.aurika.ecliptor.handler.SingularDataHandler;
import net.aurika.ecliptor.managers.BaseDataManager;
import net.aurika.ecliptor.managers.base.DataManager;
import net.aurika.util.collection.nonnull.NonNullMap;
import net.aurika.util.scheduler.DelayedRepeatingTask;
import net.aurika.util.string.StringPadder;
import net.aurika.util.string.Strings;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;

public abstract class AurikaDataCenter extends BaseDataManager {

  protected final @NotNull DatabaseType databaseType;
  protected long lastSignalledSave;
  protected final @NotNull Map<Ident, DataManager<?>> registry = new NonNullMap<>();
  protected boolean hasLoadedInitials = false;
  protected final @Nullable DelayedRepeatingTask autoSaveTask;

  @Internal
  public AurikaDataCenter(@NotNull Ident id, @NotNull DatabaseType databaseType, @Nullable Duration autoSaveInterval, boolean isCacheStatic, boolean isTemporary, boolean isSmartSaving) {
    super(id, autoSaveInterval, isCacheStatic, isTemporary, isSmartSaving);
    Objects.requireNonNull(databaseType, "databaseType");
    this.databaseType = databaseType;
    if (autoSaveInterval != null) {
      this.autoSaveTask = AuspiceUser.taskScheduler().async().repeating(
          autoSaveInterval, autoSaveInterval, new AutoSave());
    } else {
      this.autoSaveTask = null;
    }
  }

  @Internal
  public boolean hasLoadedInitials() {
    return this.hasLoadedInitials;
  }

  public <T extends DataManager<?>> T register(T dataManager) {
    this.registry.put(dataManager.key(), dataManager);
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

    for (DataManager<?> dataManager : this.getAllDataManagers().stream().sorted(
        Comparator.comparing((dataManager) -> dataManager.key().asDataString())).toList()) {
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
      var3.pad(
          "\n  &7| &3" + dataManager.key().asDataString() + "&8: &9", (var10 == null ? var7 : "&cFailed") + "&7/&9" + var6,
          " &8(&9" + var14 + "ms&8)"
      );
    }

    MessageHandler.sendConsolePluginMessage("&2" + var1.description + " Save&8: " + var3.getPaddedString(""));
    errs.forEach(Throwable::printStackTrace);
    if (var1 == AurikaDataCenter.SaveType.SIGNALLED) {
      this.lastSignalledSave = System.currentTimeMillis();
    }
  }

  public @NotNull NSKeyMap<DataManager<?>> getRegistry() {
    return this.registry;
  }

  public <K, T extends KeyedDataObject<K>> KeyedDatabase<K, T> constructDatabase(String var1, String var2, KeyedDataHandler<K, T> var3) {
    return (KeyedDatabase<K, T>) this.constructDatabase0(var1, var2, var3);
  }

  public <T extends DataObject> SingularDatabase<T> constructDatabase(String var1, String var2, SingularDataHandler<T> var3) {
    return (SingularDatabase<T>) this.constructDatabase0(var1, var2, var3);
  }

  public @NotNull DatabaseType getDatabaseType() {
    return this.databaseType;
  }

  protected abstract <T extends DataObject> Database<T> constructDatabase0(String var1, String var2, DataHandler<T> dataHandler);

  public Collection<DataManager<?>> getAllDataManagers() {
    return this.registry.values();
  }

  public void signalFullSave() {
    Duration var1 = Duration.ofMillis(this.lastSignalledSave).plus(Duration.ofSeconds(30L));
    if (!Duration.ofMillis(System.currentTimeMillis()).minus(var1).isNegative()) {
      this.saveAll(AurikaDataCenter.SaveType.SIGNALLED);
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

  private final class AutoSave implements Runnable {

    private AutoSave() {
    }

    public void run() {
      AurikaDataCenter.this.saveAll(AurikaDataCenter.SaveType.AUTO);
    }

  }

  public enum SaveType {
    AUTO,
    SIGNALLED,
    MANUAL;

    final String description = Strings.capitalize(this.name());
  }

}
