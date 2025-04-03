@file:JvmName("LandContractions")
@file:Suppress("unused")

package top.mckingdom.powerfulterritory.data

import org.kingdoms.constants.base.KeyedKingdomsObject
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.abstraction.data.DeserializationContext
import org.kingdoms.constants.land.abstraction.data.SerializationContext
import org.kingdoms.constants.metadata.KingdomMetadata
import org.kingdoms.constants.metadata.KingdomMetadataHandler
import org.kingdoms.constants.namespace.Namespace
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.data.database.dataprovider.SectionCreatableDataSetter
import org.kingdoms.data.database.dataprovider.SectionableDataGetter
import org.kingdoms.data.database.dataprovider.SectionableDataSetter
import org.kingdoms.locale.Language
import org.kingdoms.locale.SupportedLanguage
import top.mckingdom.powerfulterritory.PowerfulTerritoryAddon
import top.mckingdom.powerfulterritory.configs.PowerfulTerritoryConfig
import top.mckingdom.powerfulterritory.constants.land_contractions.ContractionLandProperties
import top.mckingdom.powerfulterritory.constants.land_contractions.LandContraction
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

//typealias ContractionsData = HashMap<LandContraction, HashSet<UUID>>

class LandContractionsData(
  val contractions: HashMap<LandContraction, ContractionLandData>,
  var keepDataWhenUnClaim: Boolean
)

class ContractionLandData(
  val allocationReceivers: HashMap<KingdomPlayer, Allocations>,
  val properties: ContractionLandProperties? = null
) {

  fun deallocateAll(player: KingdomPlayer): Allocations? {
    return this.allocationReceivers.remove(player)
  }
}

class Allocations(val since: Long, val value: HashMap<Long, Allocation>) {
  fun isEffective(): Boolean {
    val time: Long = System.currentTimeMillis()                 //当前系统时间减去每次分配的时长, 返回当前系统时间是否小于首次分配开始时的时间
    value.values.forEach { allocation ->
      time.minus(allocation.duration.toLong(DurationUnit.MILLISECONDS))
    }
    return time < since
  }

  fun hasAllocator(player: KingdomPlayer): Boolean {
    this.value.values.forEach {
      if (it.allocator == player) {
        return true
      }
    }
    return false
  }

  fun getAllocators(): MutableList<KingdomPlayer> {
    val out = ArrayList<KingdomPlayer>()
    this.value.values.forEach {
      out.add(it.allocator)
    }
    return out
  }

  fun getAllocatorsName(): MutableList<String> {
    val out = ArrayList<String>()
    this.value.values.forEach { allocation ->
      allocation.allocator.getOfflinePlayer().getName()?.let { out.add(it) }
    }
    return out
  }

  fun deallocate(allocator: KingdomPlayer) {
    this.value.forEach { since, allocation ->
      if (allocation.allocator == allocator) {
        this.value.remove(since)
      }
    }
  }
}

class Allocation(val duration: Duration, var allocator: KingdomPlayer)


//fun KingdomPlayer.hasLandContraction(land: Land, contraction: LandContraction) : Boolean {
//    return land.getContractions(this).contains(contraction)
//}
//
//
///**
// * Add a contraction for a kingdom player
// */
//@JvmName("addContraction")
//fun Land.addContraction(contraction: LandContraction, player: KingdomPlayer) {
//    this.getContractions()?.getOrPut(contraction) { hashSetOf() }!!.add(player.getId())
//}
//
///**
// * Remove a contraction for a kingdom player,
// * if this player don't have this contraction, it will remove nothing
// */
//@JvmName("removeContraction")
//fun Land.removeContraction(contraction: LandContraction, player: KingdomPlayer) {
//    this.getContractions()?.get(contraction)?.remove(player.getId())
//}
//
//fun Land.getContractions(player: KingdomPlayer) : Collection<LandContraction> {
//    val out = HashSet<LandContraction>()
//    this.getContractions()?.forEach { entry ->
//        if (entry.value.contains(player.getId())) {
//            out.add(entry.key)
//        }
//    }
//    return out
//}
//
///**
// * Get all contractions with a constants,
// * if this constants isn't claimed, it will return null,
// * if this constants is claimed, it will return a not null value
// */
//@JvmName("getContractions")
//@Contract("not null -> not null")
//fun Land.getContractions(): ContractionsData? {
//    if (this.isClaimed()) {
//        val data = this.getContractionsData()
//        if (data == null) {
//            this.resetContractionsData()
//            return this.getContractionsData()
//        } else {
//            return data
//        }
//    } else {
//        return null
//    }
//
//}
//
//
///**
// * Reset the contractions data of a constants
// */
//@JvmName("resetContractionsData")
//fun Land.resetContractionsData() {
//    this.getMetadata().put(
//        LandContractionMetaHandler.INSTANCE,
//        LandContractionMeta(
//            HashMap<LandContraction, HashSet<UUID>>().also {
//                PowerfulTerritory.get().getLandContractionRegistry().getRegistry().forEach { _, contraction ->
//                    it.put(contraction, HashSet())
//                }
//            }
//        )
//    )
//}
//
//@JvmName("setContractions")
//fun Land.setContractions(contractions: ContractionsData) {
//    this.getMetadata().put(LandContractionMetaHandler.INSTANCE, LandContractionMeta(contractions))
//}
//
///**
// * Get the data from the metadata,
// * You shouldn't use it except you know how to use this function
// */
//@Suppress("unchecked_cast")
//@JvmName("getContractionsData")
//fun Land.getContractionsData() : ContractionsData? {
//    return this.getMetadata().get(LandContractionMetaHandler.INSTANCE)?.getValue() as? ContractionsData
//}
//
//@JvmName("clearContractionsData")
//fun Land.clearContractionsData() {
//    this.getMetadata().remove(LandContractionMetaHandler.INSTANCE)
//}

/**
 * For command
 */
object Contractions {
  @JvmStatic
  fun initialize() {
    SupportedLanguage.entries.forEach { lang ->
      if (lang.isLoaded) {
        contractionsString.put(lang, HashMap<String, LandContraction>().also {
          PowerfulTerritoryAddon.get().getLandContractionRegistry().getRegistry().forEach { _, contraction ->
            it.put(contraction.getName(lang), contraction)
          }
        })
      }
    }
  }

  @JvmField
  val contractionsString = HashMap<Language, HashMap<String, LandContraction>>()

  @JvmStatic
  fun getContractions(starts: String, language: Language): List<String> {
    val out: MutableList<String> = ArrayList()
    contractionsString.get(language)?.keys?.forEach { contraction: String ->
      if (contraction.startsWith(starts)) {
        out.add(contraction)
      }
    }
    return out
  }

  fun getContraction(name: String, language: Language): LandContraction? {
    return contractionsString.get(language)?.get(name)
  }
}

fun KingdomPlayer.hasLandContraction(
  land: Land,
  contraction: LandContraction,
  requireEffectiveContraction: Boolean = true
): Boolean {
  return if (requireEffectiveContraction) {
    land.getContractions(this).get(contraction)?.isEffective() ?: false
  } else {
    land.getContractions(this).containsKey(contraction)
  }
}


@JvmName("allocate")
fun Land.allocate(
  contraction: LandContraction,
  player: KingdomPlayer,
  duration: Duration,
  allocator: KingdomPlayer,
  startTime: Long = System.currentTimeMillis()
): Allocation? {
  return (((this.getMetadata()
    .getOrPut(LandContractionsMetaHandler.INSTANCE) {
      LandContractionsMeta(
        LandContractionsData(
          HashMap(),
          PowerfulTerritoryConfig.LAND_CONTRACTION_UNCLAIM_KEEP_DATA_DEFAULT.getManager().getBoolean()
        )
      )
    }.value as LandContractionsData).contractions
    .getOrPut(contraction) { ContractionLandData(HashMap()) }).allocationReceivers
    .getOrPut(player) { Allocations(System.currentTimeMillis(), HashMap()) }).value
    .put(startTime, Allocation(duration, allocator))
}


@JvmName("getAllocations")
fun Land.getAllocations(contraction: LandContraction, player: KingdomPlayer): Allocations? {
  return this.getAllocationReceivers(contraction)?.get(player)
}

@JvmName("getPlayers")
fun Land.getAllocationReceivers(contraction: LandContraction): HashMap<KingdomPlayer, Allocations>? {
  return this.getContractions()?.get(contraction)?.allocationReceivers
}

@JvmName("getContractions")
fun Land.getContractions(player: KingdomPlayer): HashMap<LandContraction, Allocations> {
  val out: HashMap<LandContraction, Allocations> = HashMap()
  this.getContractions()?.forEach { contraction, data ->
    val allocations: Allocations? = data.allocationReceivers.get(player)
    if (allocations != null) {
      out.put(contraction, allocations)
    }
  }
  return out
}

@JvmName("getContractions")
fun Land.getContractions(): HashMap<LandContraction, ContractionLandData>? {
  return this.getContractionsData()?.contractions
}

@JvmName("getContractionsData")
fun Land.getContractionsData(): LandContractionsData? {
  return this.getMetadata().get(LandContractionsMetaHandler.INSTANCE)?.value as LandContractionsData?
}

class LandContractionsMeta(private var landContractions: LandContractionsData) : KingdomMetadata {
  override var value: Any
    get() = this.landContractions
    set(value) {}

  override fun serialize(
    container: KeyedKingdomsObject<*>,
    context: SerializationContext<SectionCreatableDataSetter>
  ) {
    val section0: SectionableDataSetter = context.getDataProvider().createSection()

    section0.get("contractions")
      .setMap(landContractions.contractions) { contraction, contractionNamespace, contractionLandData ->
        contractionNamespace.setString(contraction.getNamespace().asString())
        val section1: SectionableDataSetter = contractionNamespace.getValueProvider().createSection()
        section1.get("players")
          .setMap(contractionLandData.allocationReceivers) { player, playerKey, allocations ->
            playerKey.setUUID(player.getId())
            val section2 = playerKey.getValueProvider().createSection()
            section2.get("since").setLong(allocations.since)
            section2.get("history").setMap(allocations.value) { allocateTime, allocationKey, allocation ->
              allocationKey.setLong(allocateTime)
              val information = allocationKey.getValueProvider().createSection()
              information.setUUID("allocator", allocation.allocator.getId())
              information.setLong("duration", allocation.duration.toLong(DurationUnit.MILLISECONDS))
            }
          }
        contractionLandData.properties?.serializeProperties(section1.get("properties"))
        //TODO
      }
    section0.get("keepDataWhenUnClaim").setBoolean(landContractions.keepDataWhenUnClaim)
  }

  override fun shouldSave(container: KeyedKingdomsObject<*>): Boolean {
    return container is Land || landContractions.keepDataWhenUnClaim
  }
}
/*

"PowerfulTerritory:LAND_CONTRACTION": {
    "contractions": {
        contractionName: {
            "players": {
                playerUUID: {
                    "history": {
                        allocateTime: {
                            "allocator": allocatorUUID
                            "duration": allocateDuration
                            "metadata": kingdomMetadata              //TODO? KingdomsObject太难弄了
                        }...
                    },
                    "settings": {}                                   //TODO? 玩家对于某土地上某租约的私有设置
                }...
            },
            "properties": {}                                           //TODO? 玩家在这块土地上的有关土地租约的私有设置
        }...
    }
    "keepDataWhenUnClaim": boolean
}


*/

class LandContractionsMetaHandler private constructor() :
  KingdomMetadataHandler(PowerfulTerritoryAddon.buildNS("LAND_CONTRACTION")) {
  override fun deserialize(
    container: KeyedKingdomsObject<*>,
    context: DeserializationContext<SectionableDataGetter>
  ): KingdomMetadata {
    val provider = context.getDataProvider()

    return LandContractionsMeta(
      LandContractionsData(
        provider.get("contractions")
          .asMap(HashMap()) { contractionsMap, contractionNamespace, contractionLandData ->
            val contraction = PowerfulTerritoryAddon.get().getLandContractionRegistry()
              .getRegistered(Namespace.fromString(contractionNamespace.asString()!!))  // TODO nullability
            if (contraction != null) {
              contractionsMap.put(
                contraction, ContractionLandData(
                  contractionLandData.get("players")
                    .asMap(HashMap()) { playersMap, playerUUID, playerData ->
                      playersMap.put(
                        KingdomPlayer.getKingdomPlayer(playerUUID.asUUID()!!),  // TODO nullability
                        Allocations(
                          playerData.get("since").asLong(),
                          playerData.get("history")
                            .asMap(HashMap()) { historyMap, allocateTime, allocation ->
                              historyMap.put(
                                allocateTime.asLong(),
                                Allocation(
                                  (allocation.get("duration").asLong()).toDuration(
                                    DurationUnit.MILLISECONDS
                                  ),
                                  KingdomPlayer.getKingdomPlayer(
                                    allocation.get("allocator")
                                      .asUUID()!! // TODO nullability
                                  )
                                )
                              )
                            }
                        )
                      )
                    },
                  contraction.deserializeProperties(contractionLandData.get("properties"))
                )
              )
            }
          },
        provider.get("keepDataWhenUnClaim").asBoolean()
      )
    )
  }

  companion object {
    @JvmField
    val INSTANCE = LandContractionsMetaHandler()
  }
}


//class LandContractionMeta(private var landContractions: ContractionsData) : KingdomMetadata {
//    override fun getValue(): Any {
//        return this.landContractions
//    }
//
//    @Suppress("unchecked_cast")
//    override fun setValue(o: Any) {
//        this.landContractions = o as ContractionsData
//    }
//
//    override fun serialize(container: KeyedKingdomsObject<*>, context: SerializationContext<SectionCreatableDataSetter>) {
//        context.getDataProvider().setMap(landContractions) { contraction, keyProvider, players ->
//            keyProvider.setString(contraction.getNamespace().asDataString())
//            keyProvider.getValueProvider().setCollection(players) { playerKeyProvider, player ->
//                playerKeyProvider.setUUID(player)
//            }
//        }
//    }
//
//    override fun shouldSave(container: KeyedKingdomsObject<*>): Boolean {
//        return container is Land
//    }
//}
//
//
//class LandContractionMetaHandler private constructor() : KingdomMetadataHandler(Namespace("PowerfulTerritory", "LAND_CONTRACTION")) {
//    override fun deserialize(
//        container: KeyedKingdomsObject<*>,
//        dataGetter: DeserializationContext<SectionableDataGetter>
//    ): KingdomMetadata {
//        return LandContractionMeta(dataGetter.getDataProvider().asMap(hashMapOf()) { map, key, value ->
//            val contraction : LandContraction? = PowerfulTerritory.get().getLandContractionRegistry().getRegistered(Namespace.fromConfigString(key.asString()))
//            if (contraction == null) {
//                PowerfulTerritoryLogger.warn("Unknown constants contraction: " + key.asString() + ", ignore it")
//                return@asMap
//            }
//            val players : HashSet<UUID> = value.asCollection(HashSet()) { set, dataGetter ->
//                set.add(dataGetter.asUUID())
//            }
//            map.put(contraction, players)
//        })
//    }
//
//    companion object {
//        @JvmField
//        val INSTANCE: LandContractionMetaHandler = LandContractionMetaHandler()
//    }
//}
