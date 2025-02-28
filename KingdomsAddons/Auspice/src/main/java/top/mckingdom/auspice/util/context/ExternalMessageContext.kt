package top.mckingdom.auspice.util.context

import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider
import org.kingdoms.locale.provider.CascadingMessageContextProvider
import java.util.function.Function

private val MAIN_FACTORIES = HashMap<Class<*>, ExternalMessageContextEditsFactory<*>>()

/**
 * Gets a [ExternalMessageContextEditsFactory] by [type], the returned
 * factory is one-to-one to the provided [type].
 */
@Suppress("UNCHECKED_CAST")
fun <T> getExternalMessageContextEditsFactory(type: Class<T>): ExternalMessageContextEditsFactory<T> {
    var factory = MAIN_FACTORIES[type]
    if (factory == null) {
        factory = ExternalMessageContextEditsFactory(type)
        MAIN_FACTORIES[type] = factory
    }
    return factory as ExternalMessageContextEditsFactory<T>
}

/**
 * @see getExternalMessageContextEditsFactory
 */
inline fun <reified T> getExternalMessageContextEditsFactory() = getExternalMessageContextEditsFactory(T::class.java)

/**
 * A class to resolve the problem of some class don't have message context edits.
 */
open class ExternalMessageContextEditsFactory<T>(val type: Class<T>) {
    protected val extMessageContextEdits: MutableMap<String, Function<T, Any?>> = HashMap()

    open fun addExtMessageContextEdit(name: String, editProvider: Function<T, Any?>) {
        extMessageContextEdits[name] = editProvider
    }

    open fun removeExtMessageContextEdit(name: String) {
        extMessageContextEdits.remove(name)
    }

    /**
     * Adds message context edits into [messageContext].
     *
     * This will add the external edits and the default implemented edits [CascadingMessageContextProvider.addMessageContextEdits].
     * Note: the external message context edits will be overridden by the default implementation [CascadingMessageContextProvider.addMessageContextEdits].
     */
    open fun addMessageContextEntries(obj: T, messageContext: MessagePlaceholderProvider) {
        for (edit in extMessageContextEdits) {
            messageContext.raw(edit.key, edit.value.apply(obj))
        }
        if (obj is CascadingMessageContextProvider) {
            try {  // override the ext message context edits
                obj.addMessageContextEdits(messageContext)
            } catch (_: Exception) {  //
            }
        }
    }

    open fun addMessageContextEntry(obj: T, messageContext: MessagePlaceholderProvider, editName: String) {
        val editProvider = extMessageContextEdits[editName]
        if (editProvider != null) {
            messageContext.raw(editName, editProvider.apply(obj))
        }
    }
}
