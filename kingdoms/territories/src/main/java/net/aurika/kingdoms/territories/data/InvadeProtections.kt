@file:JvmName("InvadeProtections")
@file:Suppress("unused")

package net.aurika.kingdoms.territories.data

import net.aurika.kingdoms.territories.TerritoriesAddon
import net.aurika.kingdoms.territories.constant.invade_protection.InvadeProtection
import net.aurika.kingdoms.territories.constant.invade_protection.InvadeProtectionRegistry
import net.aurika.kingdoms.territories.constant.invade_protection.StandardInvadeProtection
import net.aurika.kingdoms.territories.constant.land.contraction.LandContraction
import net.aurika.kingdoms.territories.util.PowerfulTerritoryLogger
import org.kingdoms.constants.base.KeyedKingdomsObject
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.abstraction.data.DeserializationContext
import org.kingdoms.constants.land.abstraction.data.SerializationContext
import org.kingdoms.constants.land.location.SimpleChunkLocation
import org.kingdoms.constants.metadata.KingdomMetadata
import org.kingdoms.constants.metadata.KingdomMetadataHandler
import org.kingdoms.constants.metadata.StandardKingdomMetadata
import org.kingdoms.constants.namespace.Namespace
import org.kingdoms.data.database.dataprovider.SectionCreatableDataSetter
import org.kingdoms.data.database.dataprovider.SectionableDataGetter
import org.kingdoms.utils.internal.cast
import java.util.*

//命名空间根据服务器需求进行修改
fun getInvadeProtection(chunk: SimpleChunkLocation): InvadeProtection? {
  return getInvadeProtection(chunk.land)
}

fun getInvadeProtection(land: Land?): InvadeProtection? {
  if (land == null || !land.isClaimed) {
    return null
  }
  val meta: KingdomMetadata? = land.getMetadata().get(InvadeProtectionMetaHandler.INSTANCE)
  return if (meta == null) {
    StandardInvadeProtection.NO_PROTECTION
  } else {
    Objects.requireNonNullElse(
      meta.value as InvadeProtection,
      StandardInvadeProtection.NO_PROTECTION
    )
  }
}

fun setInvadeProtection(chunk: SimpleChunkLocation, status: InvadeProtection) {
  setInvadeProtection(chunk.land, status)
}

fun setInvadeProtection(land: Land?, status: InvadeProtection) {
  if (land != null && land.isClaimed) {
    if (status === StandardInvadeProtection.NO_PROTECTION) {
      land.getMetadata().remove(InvadeProtectionMetaHandler.INSTANCE)
    } else {
      land.getMetadata()
        .put(InvadeProtectionMetaHandler.INSTANCE, StandardKingdomMetadata(status.namespace.asString()))
    }
  }
}

class InvadeProtectionMeta(override var value: Any) : KingdomMetadata {

  override fun serialize(
    container: KeyedKingdomsObject<*>,
    context: SerializationContext<SectionCreatableDataSetter>
  ) {
    context.getDataProvider().setString(this.value.cast<LandContraction>().namespace.asNormalizedString())
  }

  override fun shouldSave(container: KeyedKingdomsObject<*>): Boolean {
    return container is Land
  }
}

class InvadeProtectionMetaHandler internal constructor() :
  KingdomMetadataHandler(TerritoriesAddon.buildNS("INVADE_PROTECTION")) {
  override fun deserialize(
    container: KeyedKingdomsObject<*>,
    context: DeserializationContext<SectionableDataGetter>
  ): KingdomMetadata {
    val namespace: Namespace = context.getDataProvider().asString()?.let { Namespace.fromString(it) }
      ?: StandardInvadeProtection.NO_PROTECTION.namespace
    val invadeProtection: InvadeProtection? = InvadeProtectionRegistry.get().getRegistered(namespace)
    if (invadeProtection == null) {
      PowerfulTerritoryLogger.warn("Unknown InvadeProtection: $namespace, ignore it.")
      return InvadeProtectionMeta(StandardInvadeProtection.NO_PROTECTION)
    } else {
      return InvadeProtectionMeta(invadeProtection)
    }
  }

  companion object {
    @JvmField
    val INSTANCE: KingdomMetadataHandler = InvadeProtectionMetaHandler()
  }
}