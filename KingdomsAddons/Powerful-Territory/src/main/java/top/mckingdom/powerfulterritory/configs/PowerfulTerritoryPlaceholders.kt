package top.mckingdom.powerfulterritory.configs

import org.kingdoms.constants.land.Land
import org.kingdoms.locale.placeholders.*
import top.mckingdom.powerfulterritory.constants.land_categories.LandCategoryRegistry
import top.mckingdom.powerfulterritory.constants.land_categories.StandardLandCategory
import top.mckingdom.powerfulterritory.data.category
import java.util.*
import java.util.function.Function

enum class PowerfulTerritoryPlaceholders(
    override val default: Any,
    override val translator: Function<KingdomsPlaceholderTranslationContext, Any?>,
    override var configuredDefaultValue: Any? = default
) : EnumKingdomsPlaceholderTranslator {
    KINGDOM_LANDS_OF_CATEGORY_NONE(0, { context ->
        var amount = 0
        context.getKingdom()?.lands?.forEach { land: Land ->
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
            @PhParam(p0 = "category") category: String
        ): Any {
            val kingdom = context.getKingdom()
            var amount = 0
            val category1 = LandCategoryRegistry.getLandCategoryFromConfigName(category) ?: return 0

            kingdom?.lands?.forEach { land: Land ->
                if (land.category == category1) {
                    amount++
                }
            }
            return amount
        }
    }),

    ;

    override fun getFunctions(): Map<String, FunctionalPlaceholder.CompiledFunction>? {
        return super<EnumKingdomsPlaceholderTranslator>.getFunctions()
    }

    override fun translate(p0: KingdomsPlaceholderTranslationContext): Any? {
        return super<EnumKingdomsPlaceholderTranslator>.translate(p0)
    }

    companion object {
        @JvmStatic
        fun init() {
        }
    }
}