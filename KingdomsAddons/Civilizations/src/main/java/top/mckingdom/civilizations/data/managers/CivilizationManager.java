package top.mckingdom.civilizations.data.managers;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.data.centers.KingdomsDataCenter;
import org.kingdoms.data.database.base.KeyedKingdomsDatabase;
import org.kingdoms.data.managers.base.KeyedDataManager;
import org.kingdoms.utils.internal.IndexedHashMap;
import org.kingdoms.utils.internal.string.QuantumString;
import top.mckingdom.civilizations.CivilizationsAddon;
import top.mckingdom.civilizations.config.CivilizationsAddonConfig;
import top.mckingdom.civilizations.constants.civilization.Civilization;

import java.util.*;

public class CivilizationManager extends KeyedDataManager<UUID, Civilization> {
  private static final Map<QuantumString, UUID> a = new HashMap();
  private static final IndexedHashMap<UUID, Integer> b = new IndexedHashMap(new UUID[0]);
  private static final HashSet<UUID> civilizationsUUID = new HashSet<>();
  private static final HashMap<UUID, Civilization> civilizations = new HashMap<>();

  public static Set<UUID> getCivilizationsIdSet() {
    return civilizationsUUID;
  }

  static {

  }

  public static CivilizationManager INSTANCE;

  public CivilizationManager(KeyedKingdomsDatabase<UUID, Civilization> var2, boolean var3, KingdomsDataCenter var4) {
    super(CivilizationsAddon.buildNS("CIVILIZATIONS"), var2, var3, var4);
    INSTANCE = this;
  }

  public static Civilization getCivilization(UUID uuid) {
    return civilizations.get(uuid);
  }

  public static @NotNull Map<QuantumString, UUID> getNames() {
    return Collections.unmodifiableMap(a);
  }

  public static QuantumString toQuantumName(String str) {
    return new QuantumString(str, !CivilizationsAddonConfig.CIVILIZATION_NAME_CASE_SENSITIVE.getBoolean());
  }

  public static Map<UUID, Civilization> getCivilizations() {
    return civilizations;
  }

  public Civilization getData(UUID uuid) {
    return civilizations.get(uuid);
  }

}
