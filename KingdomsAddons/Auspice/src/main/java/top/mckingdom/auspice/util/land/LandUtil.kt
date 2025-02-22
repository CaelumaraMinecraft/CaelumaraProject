@file:JvmName("LandUtil")

package top.mckingdom.auspice.util.land

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

class LandExternalMessageContextEditsFactory {
    private val landMessageContextEdits: MutableMap<String, Function<Land, Any?>> = HashMap()

    fun addExtMessageContextEdit(name: String, editProvider: Function<Land, Any?>) {
        landMessageContextEdits[name] = editProvider
    }

    fun removeExtMessageContextEdit(name: String) {
        landMessageContextEdits.remove(name)
    }

    /**
     * Adds message context edits into [messageContext].
     *
     * This will add the external edits and the default implemented edits [Land.addMessageContextEdits].
     * Note: the external message context edits will be overridden by the default implementation [Land.addMessageContextEdits].
     */
    fun addMessageContextEntries(land: Land, messageContext: MessagePlaceholderProvider) {
        for (edit in landMessageContextEdits) {
            messageContext.raw(edit.key, edit.value.apply(land))
        }
        try {  // override the ext message context edits
            land.addMessageContextEdits(messageContext)
        } catch (_: Exception) {
        }
    }

    fun addMessageContextEntry(land: Land, messageContext: MessagePlaceholderProvider, editName: String) {
        val editProvider = landMessageContextEdits[editName]
        if (editProvider != null) {
            messageContext.raw(editName, editProvider.apply(land))
        }
    }
}
