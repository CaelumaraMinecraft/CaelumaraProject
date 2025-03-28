package top.mckingdom.civilizations.constants.civilization;

import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.base.KeyedKingdomsObject;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.land.location.SimpleLocation;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.MarkingCivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.objects.CivilizationMemberKingdom;
import top.mckingdom.civilizations.constants.civilization.member.objects.CivilizationMemberNation;
import top.mckingdom.civilizations.constants.civilization.relation.CivilizationRelation;
import top.mckingdom.civilizations.constants.civilization.relation.StandardCivilizationRelation;
import top.mckingdom.civilizations.data.managers.CivilizationManager;

import java.util.*;

@SuppressWarnings("unused")
public class Civilization extends KeyedKingdomsObject<UUID> {


  //======== Id =========

  private final UUID dataKey;

  @Override
  public @NonNull UUID getKey() {
    return this.dataKey;
  }


  //====================   Name   =====================

  private @NotNull String name;

  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    Objects.requireNonNull(name, "name");
    this.name = name;
  }


  //====================   Tag   =====================

  private String tag;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  //=====================   Cores   =====================
  private Set<SimpleLocation> cores;

  public Set<SimpleLocation> getCores() {
    return cores;
  }

  public void setCores(Set<SimpleLocation> cores) {
    this.cores = cores;
  }

  /**
   * @param ignoreLimit 如果为true,则忽略文明的的核心上限强制添加
   */
  public void addCore(SimpleLocation location, boolean ignoreLimit) {
    if (!ignoreLimit) {
      if (cores.size() >= maxCores) {
        return;
      }
    }
    this.cores.add(location);
  }

  private int maxCores;

  public int getMaxCores() {
    return maxCores;
  }

  public void setMaxCores(int maxCores) {
    this.maxCores = maxCores;
  }


  //=============   Nodes   =====================

  private Set<SimpleLocation> nodes;

  public Set<SimpleLocation> getNodes() {
    return nodes;
  }

  public void setNodes(Set<SimpleLocation> nodes) {
    this.nodes = nodes;
  }

  private int maxNodes;

  public int getMaxNodes() {
    return this.maxNodes;
  }

  public void setMaxNodes(int maxNodes) {
    this.maxNodes = maxNodes;
  }


  //====================   Lands   ======================

  private Set<SimpleChunkLocation> lands;

  public Set<SimpleChunkLocation> getLands() {
    return lands;
  }

  public void setLands(Set<SimpleChunkLocation> lands) {
    this.lands = lands;
  }

  public void addLand(SimpleChunkLocation location) {
    this.lands.add(location);
  }

  private int maxLands;

  public int getMaxLands() {
    return this.maxLands;
  }

  public void setMaxLands(int maxLands) {
    this.maxLands = maxLands;
  }

  //===========================   Homes   ===========================
  private Set<Location> homes;

  public Set<Location> getHomes() {
    return this.homes;
  }

  public void setHomes(Set<Location> homes) {
    this.homes = homes;
  }

  /**
   * @param ignoreLimit 如果为true,则忽略文明的的home上限强制添加
   */
  public void addHome(Location home, boolean ignoreLimit) {
    if (ignoreLimit) {
      homes.add(home);
    } else {
      if (homes.size() < maxHomes) {
        homes.add(home);
      }
    }
  }

  private int maxHomes;

  public int getMaxHomes() {
    return maxHomes;
  }

  public void setMaxHomes(int maxHomes) {
    this.maxHomes = maxHomes;
  }


  //=======================   Level   =====================

  private int level;

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }


  //====================   relation   =====================

  private final Map<UUID, CivilizationRelation> relations;

  public Map<UUID, CivilizationRelation> getRelations() {
    return this.relations;
  }

  public CivilizationRelation getRelationWith(Civilization civilization) {
    if (civilization == this) {
      return StandardCivilizationRelation.SELF;
    } else {
      CivilizationRelation relation = this.relations.get(civilization.getKey());
      return relation == null ? StandardCivilizationRelation.NEUTRAL : relation;
    }
  }

  //=====================   Members   =====================

  private final Set<CivilizationMember<?>> topMembers;

  private int maxMembers;

  public Set<CivilizationMember<?>> unsafeGetMembers() {
    return this.topMembers;
  }

  public Set<CivilizationMember<?>> getTopMembers() {
    return Collections.unmodifiableSet(this.topMembers);
  }

  public CivilizationMember<?> findMember(MarkingCivilizationMember<?> target) {
    for (CivilizationMember<?> m : this.topMembers) {
      CivilizationMember<?> founded;
      if ((founded = m.findSubordinate(target)) != null) {
        return founded;
      }
    }
    return null;
  }

  public boolean isTopMember(MarkingCivilizationMember<?> m) {
    return this.topMembers.contains(m);
  }

  public boolean isTopMember(Object o) {
    return getTopMember(o) != null;
  }

  /**
   * @param o A civilization member object or a object included in civilization member
   */
  @Nullable
  public CivilizationMember<?> getTopMember(Object o) {
    for (CivilizationMember<?> m : this.topMembers) {
      if (m.equals(o)) {
        return m;
      }
      if (m.getKey().equals(o)) {
        return m;
      }
    }
    return null;
  }

  public boolean isTopMember(Kingdom kingdom) {
    return getTopMember(kingdom) != null;
  }

  public CivilizationMemberKingdom getTopMember(Kingdom kingdom) {
    for (CivilizationMember<?> m : this.topMembers) {
      if (m instanceof CivilizationMemberKingdom km) {
        if (km.getKey().equals(kingdom)) {
          return km;
        }
      }
    }
    return null;
  }

  public boolean isTopMember(Nation nation) {
    return getTopMember(nation) != null;
  }

  public CivilizationMemberNation getTopMember(Nation nation) {
    for (CivilizationMember<?> m : this.topMembers) {
      if (m instanceof CivilizationMemberNation nm) {
        if (nm.getKey().equals(nation)) {
          return nm;
        }
      }
    }
    return null;
  }

  public int getMaxMembers() {
    return maxMembers;
  }

  public void setMaxMembers(int maxMembers) {
    this.maxMembers = maxMembers;
  }

  //=============   Since   ======================
  private long since;

  public long getSince() {
    return since;
  }

  public void setSince(long since) {
    this.since = since;
  }


  //============== main =====================

  /**
   * 数据库已有的
   */
  public Civilization(UUID dataKey,
                      String name,
                      String tag,
                      Set<SimpleLocation> cores,
                      int maxCores,
                      Set<SimpleLocation> nodes,
                      int maxNodes,
                      Set<SimpleChunkLocation> lands,
                      int maxLands,
                      Set<Location> homes,
                      int maxHomes,
                      Set<CivilizationMember<?>> topMembers,
                      Map<UUID, CivilizationRelation> relations,
                      long since
  ) {
    this.dataKey = dataKey;
    this.name = name;
    this.tag = tag;
    this.cores = cores;
    this.maxCores = maxCores;
    this.nodes = nodes;
    this.maxNodes = maxNodes;
    this.lands = lands;
    this.maxLands = maxLands;
    this.homes = homes;
    this.maxHomes = maxHomes;
    this.topMembers = topMembers;
    this.relations = relations;
    this.since = since;
  }

  /**
   * new
   */
  public Civilization(String name) {
    this.dataKey = UUID.randomUUID();
    this.name = name;
    this.tag = "default";  //TODO
    this.cores = new HashSet<>();
    this.maxCores = 2; //TODO
    this.nodes = new HashSet<>();
    this.maxNodes = 10;  //TODO
    this.lands = new HashSet<>();
    this.maxLands = 500;  //TODO
    this.homes = new HashSet<>();
    this.maxHomes = 5;  //TODO
    this.topMembers = new HashSet<>();
    this.maxMembers = 20;  //TODO
    this.relations = new HashMap<>();
    this.since = System.currentTimeMillis();
  }

  public Civilization(UUID uuid) {
    this.dataKey = uuid;
    this.name = "default";
    this.tag = "default";  //TODO
    this.cores = new HashSet<>();
    this.maxCores = 2; //TODO
    this.nodes = new HashSet<>();
    this.maxNodes = 10;  //TODO
    this.lands = new HashSet<>();
    this.maxLands = 500;  //TODO
    this.homes = new HashSet<>();
    this.maxHomes = 5;  //TODO
    this.topMembers = new HashSet<>();
    this.maxMembers = 20;  //TODO
    this.relations = new HashMap<>();
    this.since = System.currentTimeMillis();
  }

  public void createCivilization(String name) {

  }

  public static Civilization getCivilization(UUID uuid) {
    Objects.requireNonNull(uuid, "Civilization ID can not be null");
    return CivilizationManager.INSTANCE.getData(uuid);
  }

}
