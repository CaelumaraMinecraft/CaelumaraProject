package net.aurika.auspice.service.vault.constants;

import net.aurika.auspice.constants.ecomony.balance.Balance;
import net.aurika.auspice.service.vault.ServiceVault;
import net.aurika.validate.Validate;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VaultBalance implements Balance<OfflinePlayer> {

  private final @NotNull OfflinePlayer account;

  public VaultBalance(@NotNull OfflinePlayer account) {
    Validate.Arg.notNull(account, "account");
    this.account = account;
  }

  public double get() {
    return ServiceVault.getMoney(this.account);
  }

  public double set(@NotNull Number value) {
    Objects.requireNonNull(value, "value");
    double bal = this.get();
    double dbVal = value.doubleValue();
    if (bal > dbVal) {
      ServiceVault.withdraw(this.account, bal - dbVal);
    } else if (bal < dbVal) {
      ServiceVault.deposit(this.account, dbVal - bal);
    }

    return dbVal;
  }

  @NotNull
  public VaultCurrency getEconomy() {
    return VaultCurrency.INSTANCE;
  }

}
