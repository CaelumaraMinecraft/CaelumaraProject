package net.aurika.auspice.constants.ecomony.currency.generators;

import org.jetbrains.annotations.NotNull;
import top.auspice.config.sections.old.test_02.ConfigSection;
import net.aurika.auspice.constants.ecomony.currency.CurrencyType;
import net.aurika.auspice.constants.ecomony.currency.CurrencyRegistry;
import net.aurika.auspice.constants.ecomony.currency.bill.CurrencyEntryList;

public interface CurrencyListGenerator<T> {

    @NotNull
    CurrencyEntryList<T> generate(ConfigSection section);

    @SuppressWarnings("unchecked")
    default CurrencyEntryList<T> generalGenerate(ConfigSection section, Class<T> clazz) {
        CurrencyEntryList.TradeType type = section.getEnum(new String[]{"expend"}, CurrencyEntryList.TradeType.class);
        if (type == null) {
            type = CurrencyEntryList.TradeType.EXPEND;  //TODO
        }

        CurrencyEntryList<T> out = new CurrencyEntryList<>(type);
        ConfigSection entries = section.getSection("entries");
        if (entries != null) {
            entries.getSets(false).forEach((key, amount) -> {
                CurrencyType<T, ?> currency = (CurrencyType<T, ?>) CurrencyRegistry.getRegistered(key);
                if (currency != null && currency.getTargetClass() == clazz) {
                    out.add(currency.getAmount(amount));
                }
            });
        }
        return out;
    }

}