package net.aurika.kingdoms.auspice.costs;

import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;

public class CurrencyRegistry {

  private static final HashMap<String, Currency<?, ?>> currencies = new HashMap<>();

  public static void register(Currency<?, ?> currency) {
    if (currencies.put(currency.getKey(), currency) != null) {
      throw new IllegalArgumentException(currency.getKey() + "was already registered");
    }
  }

  public static @Nullable Currency<?, ?> getRegistered(String s) {
    return currencies.get(s);
  }

  public static void init() {
    register(StandardCurrencies.KINGDOM_BANK);
    register(StandardCurrencies.KINGDOM_RESOURCE_POINTS);
    register(StandardCurrencies.KINGDOM_MAX_LANDS_MODIFIER);
    register(StandardCurrencies.NATION_BANK);
    register(StandardCurrencies.NATION_RESOURCE_POINTS);
    if (Bukkit.getPluginManager().getPlugin("Kingdoms-Addon-Peace-Treaties") != null) {
      register(StandardCurrencies.RELATED_KINGDOMS_WAR_POINTS);
    }
  }

  private CurrencyRegistry() {

  }

}
