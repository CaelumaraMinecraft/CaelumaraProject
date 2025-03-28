package top.mckingdom.auspice.costs.generators

import org.kingdoms.constants.land.Land
import org.kingdoms.utils.config.ConfigSection
import top.mckingdom.auspice.costs.statistics.CurrencyEntryList

class LandCurrencyListGenerator : CurrencyListGenerator<Land> {
  override fun generate(section: ConfigSection): CurrencyEntryList<Land> {
    return generalGenerate(section, Land::class.java)
  }

  companion object {
    private val instance = LandCurrencyListGenerator()

    @JvmStatic
    fun get() = instance
  }
}