package net.aurika.auspice.service.vault.constants;

import net.aurika.auspice.constants.ecomony.currency.AbstractNumberCurrency;
import net.aurika.auspice.constants.ecomony.currency.bill.CurrencyEntry;
import net.aurika.auspice.service.vault.ServiceVault;
import net.aurika.auspice.utils.math.MathUtils;
import net.aurika.namespace.NSedKey;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public final class VaultCurrency extends AbstractNumberCurrency<OfflinePlayer> {

  @NotNull
  public static final VaultCurrency INSTANCE = new VaultCurrency();

  private VaultCurrency() {
    super("vault", OfflinePlayer.class, new NSedKey("VAULT", "MAIN"));
  }

  public boolean isAvailable() {
    return ServiceVault.isAvailable(ServiceVault.Component.ECO);
  }

  public CurrencyEntry<OfflinePlayer, Number> getAmount(String amountString) {
    return new CurrencyEntry<>(this, MathUtils.parseDouble(amountString));
  }

  public CurrencyEntry<OfflinePlayer, Number> getAmount(Object amount) {
    return null;
  }

  public boolean canExpend(OfflinePlayer target, Number amount) {
    return false;
  }

  public void forceExpend(OfflinePlayer target, Number amount) {

  }

  public boolean canRefund(OfflinePlayer target, Number amount) {
    return false;
  }

  public void forceRefund(OfflinePlayer target, Number amount) {

  }

}
