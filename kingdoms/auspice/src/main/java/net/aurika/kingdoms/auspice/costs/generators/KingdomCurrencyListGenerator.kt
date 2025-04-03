package net.aurika.kingdoms.auspice.costs.generators

import net.aurika.kingdoms.auspice.costs.statistics.CurrencyEntryList
import org.kingdoms.constants.group.Kingdom
import org.kingdoms.utils.config.ConfigSection

class KingdomCurrencyListGenerator : CurrencyListGenerator<Kingdom> {
  override fun generate(section: ConfigSection): CurrencyEntryList<Kingdom> {
    return generalGenerate(section, Kingdom::class.java)
  }

  companion object {
    private val instance = KingdomCurrencyListGenerator()

    @JvmStatic
    fun get(): KingdomCurrencyListGenerator {
      return instance
    }
  }
}
