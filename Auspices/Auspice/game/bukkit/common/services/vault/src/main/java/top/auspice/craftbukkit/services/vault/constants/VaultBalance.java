package top.auspice.craftbukkit.services.vault.constants;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.ecomony.balance.Balance;
import top.auspice.craftbukkit.services.vault.ServiceVault;

import java.util.Objects;

public final class VaultBalance implements Balance<OfflinePlayer> {
    @NotNull
    private final OfflinePlayer account;

    public VaultBalance(@NotNull OfflinePlayer account) {
        Objects.requireNonNull(account, "account");
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
