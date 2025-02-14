package top.mckingdom.auspice.costs.generators;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.utils.config.ConfigSection;
import top.mckingdom.auspice.costs.Currency;
import top.mckingdom.auspice.costs.CurrencyRegistry;
import top.mckingdom.auspice.costs.statistics.CurrencyEntryList;
import top.mckingdom.auspice.costs.statistics.CurrencyFlowRecord;

public interface CurrencyListGenerator<T> {

    @NotNull
    CurrencyEntryList<T> generate(ConfigSection section);

    @SuppressWarnings("unchecked")
    default CurrencyEntryList<T> generalGenerate(ConfigSection section, Class<T> clazz) {
        boolean b = section.getBoolean("expend");
        CurrencyEntryList.TradeType circulateType;
        if (b) {
            circulateType = CurrencyEntryList.TradeType.EXPEND;
        } else {
            circulateType = CurrencyEntryList.TradeType.INCOME;
        }
        CurrencyEntryList<T> out = new CurrencyEntryList<>(circulateType);
        ConfigSection entries = section.getSection("entries");
        if (entries != null) {
            entries.getValues(false).forEach((key, amount) -> {
                Currency<T, ?> currency = (Currency<T, ?>) CurrencyRegistry.getRegistered(key);
                if (currency != null && currency.getTargetClass() == clazz) {
                    out.add(currency.getAmount(amount));
                }
            });
        }
        return out;
    }

}
