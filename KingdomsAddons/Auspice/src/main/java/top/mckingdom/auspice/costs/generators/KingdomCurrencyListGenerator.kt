package top.mckingdom.auspice.costs.generators

import org.kingdoms.constants.group.Kingdom
import org.kingdoms.utils.config.ConfigSection
import top.mckingdom.auspice.costs.statistics.CurrencyEntryList

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
