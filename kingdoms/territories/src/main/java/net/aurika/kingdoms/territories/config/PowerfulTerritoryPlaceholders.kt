package net.aurika.kingdoms.territories.config

import net.aurika.kingdoms.territories.constant.land.category.LandCategoryRegistry
import net.aurika.kingdoms.territories.constant.land.category.StandardLandCategory
import net.aurika.kingdoms.territories.data.category
import org.kingdoms.constants.land.Land
import org.kingdoms.locale.placeholders.*

enum class PowerfulTerritoryPlaceholders(
  override val default: Any,
  override val translator: PlaceholderTranslator,
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
      @PhParam("category") category: String
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

  override fun translate(context: KingdomsPlaceholderTranslationContext): Any? {
    return this.translator.apply(context)
  }

  override fun getFunctions(): Map<String, FunctionalPlaceholder.CompiledFunction>? {
    val translator = this.translator
    return if (translator is FunctionalPlaceholder) translator.functions else null
  }

  companion object {
    @JvmStatic
    fun init() {
      // <clinit>
    }
  }
}
