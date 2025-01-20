package top.mckingdom.auspice.configs

import org.kingdoms.locale.placeholders.EnumKingdomsPlaceholderTranslator
import org.kingdoms.locale.placeholders.KingdomsPlaceholderTranslationContext
import org.kingdoms.locale.placeholders.KingdomsPlaceholderTranslator
import java.util.*
import java.util.function.Function

@Suppress("unused")
enum class AuspicePlaceholder(
    private val default: Any,
    private val translator: Function<KingdomsPlaceholderTranslationContext, Any?>
) : EnumKingdomsPlaceholderTranslator {



    ;

    // override var configuredDefaultValue
    private var configuredDefaultValue: Any? = default

    init {
        KingdomsPlaceholderTranslator.register(this)
    }

    override fun getName(): String {
        return this.name.lowercase(Locale.ENGLISH)
    }


    // override val default
    override fun getDefault(): Any {
        return this.default
    }

    override fun getTranslator(): Function<KingdomsPlaceholderTranslationContext, Any?> {
        return this.translator
    }

    override fun getConfiguredDefaultValue(): Any? {
        return this.configuredDefaultValue
    }

    override fun setConfiguredDefaultValue(configuredDefaultValue: Any?) {
        this.configuredDefaultValue = configuredDefaultValue
    }

    companion object {
        @JvmStatic
        fun init() {
        }
    }
}