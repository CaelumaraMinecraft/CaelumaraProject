package top.auspice.constants.ecomony.currency;


import org.bukkit.Bukkit;
import net.aurika.namespace.NSKedRegistry;
import top.auspice.main.Auspice;

public final class CurrencyRegistry extends NSKedRegistry<Currency<?, ?>> {

    public static final CurrencyRegistry INSTANCE = new CurrencyRegistry();

    public static void registerDefaults() {
        INSTANCE.register(StandardCurrencies.KINGDOM_BANK);
        INSTANCE.register(StandardCurrencies.KINGDOM_RESOURCE_POINTS);
        INSTANCE.register(StandardCurrencies.KINGDOM_MAX_LANDS_MODIFIER);
        INSTANCE.register(StandardCurrencies.NATION_BANK);
        INSTANCE.register(StandardCurrencies.NATION_RESOURCE_POINTS);
        if (Bukkit.getPluginManager().getPlugin("Kingdoms-Addon-Peace-Treaties") != null) {
            INSTANCE.register(StandardCurrencies.RELATED_KINGDOMS_WAR_POINTS);
        }
    }

    private CurrencyRegistry() {
        super(Auspice.get(), "CURRENCY");
    }

}
