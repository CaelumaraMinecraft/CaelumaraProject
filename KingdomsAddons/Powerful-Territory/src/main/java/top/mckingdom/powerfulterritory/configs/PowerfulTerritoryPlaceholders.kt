package top.mckingdom.powerfulterritory.configs

import org.kingdoms.constants.land.Land
import org.kingdoms.locale.placeholders.*
import top.mckingdom.powerfulterritory.constants.land_categories.LandCategoryRegistry
import top.mckingdom.powerfulterritory.constants.land_categories.StandardLandCategory
import top.mckingdom.powerfulterritory.data.category
import java.util.*
import java.util.function.Function

enum class PowerfulTerritoryPlaceholders(
    private val default: Any,
    private val translator: Function<KingdomsPlaceholderTranslationContext, Any?>
) : EnumKingdomsPlaceholderTranslator {
    KINGDOM_LANDS_OF_CATEGORY_NONE(0, { context ->
        var amount = 0
        context.kingdom.lands.forEach { land ->
            if (land.category == StandardLandCategory.NONE) {
                amount++
            }
        }
        amount
    }),

    KINGDOM_LANDS_OF_CATEGORY(0, object : FunctionalPlaceholder() {
        @PhFn
        fun of(
            context: KingdomsPlaceholderTranslationContext,
            @PhParam(name = "category") category: String
        ): Any {
            val kingdom = context.getKingdom()
            var amount = 0
            val category1 = LandCategoryRegistry.getLandCategoryFromConfigName(category) ?: return 0

            kingdom.lands.forEach { land: Land ->
                if (land.category == category1) {
                    amount++
                }
            }
            return amount
        }
    }),

    ;

    private var configuredDefaultValue: Any?

    init {
        configuredDefaultValue = default
        KingdomsPlaceholderTranslator.register(this)
    }

    override fun getTranslator(): Function<KingdomsPlaceholderTranslationContext, Any?> {
        return this.translator
    }

    override fun getName(): String {
        return this.name.lowercase(Locale.ENGLISH)
    }

    //    override val default
    override fun getDefault(): Any {
        return this.default
    }

    //    override var configuredDefaultValue
    override fun getConfiguredDefaultValue(): Any? {
        return this.configuredDefaultValue
    }

    override fun setConfiguredDefaultValue(value: Any?) {
        this.configuredDefaultValue = value
    }

    companion object {
        @JvmStatic
        fun init() {
        }
    }
}