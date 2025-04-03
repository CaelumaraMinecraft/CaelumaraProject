@file:JvmName("LandUtil")

package net.aurika.kingdoms.auspice.util.land

import net.aurika.kingdoms.auspice.util.context.ExternalMessageContextEditsFactory
import org.kingdoms.constants.land.Land
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider
import java.util.function.Function

fun addExtMessageContextEdit(name: String, editProvider: Function<Land, Any?>) {
  MAIN_FACTORY.addExtMessageContextEdit(name, editProvider)
}

fun removeExtMessageContextEdit(name: String) {
  MAIN_FACTORY.removeExtMessageContextEdit(name)
}

/**
 * @see LandExternalMessageContextEditsFactory.addMessageContextEntries
 */
fun Land.addMessageContextEntries(messageContext: MessagePlaceholderProvider) {
  MAIN_FACTORY.addMessageContextEntries(this, messageContext)
}

fun Land.addMessageContextEntry(messageContext: MessagePlaceholderProvider, editName: String) {
  MAIN_FACTORY.addMessageContextEntry(this, messageContext, editName)
}

@JvmField
val MAIN_FACTORY = LandExternalMessageContextEditsFactory().apply { addStandardExtMessageContextEntries() }

fun init() {}

fun LandExternalMessageContextEditsFactory.addStandardExtMessageContextEntries() {
  this.addExtMessageContextEdit("location") { land -> land.location }
  this.addExtMessageContextEdit("kingdomId") { land -> land.kingdomId }
  this.addExtMessageContextEdit("claimer") { land -> land.claimer }
  this.addExtMessageContextEdit("claimedBy") { land -> land.claimedBy }
  this.addExtMessageContextEdit("kingdom") { land -> land.kingdom }
  this.addExtMessageContextEdit("isUnderAttack") { land -> land.isUnderAttack }
  this.addExtMessageContextEdit("since") { land -> land.since }
  this.addExtMessageContextEdit("isDataEmpty") { land -> land.isDataEmpty }
  // TODO more edits
}

open class LandExternalMessageContextEditsFactory : ExternalMessageContextEditsFactory<Land>(Land::class.java)
