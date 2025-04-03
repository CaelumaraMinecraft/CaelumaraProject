package net.aurika.kingdoms.auspice.costs.generators

import net.aurika.kingdoms.auspice.costs.statistics.CurrencyEntryList
import org.kingdoms.constants.land.Land
import org.kingdoms.utils.config.ConfigSection

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