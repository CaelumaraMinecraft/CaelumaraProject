package net.aurika.auspice.service.vault;

import net.aurika.auspice.service.api.BukkitServiceEconomy;
import net.aurika.auspice.service.vault.constants.VaultBalance;
import net.aurika.validate.Validate;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public class ServiceVault implements BukkitServiceEconomy {

  public ServiceVault() {
  }

  protected static @Nullable Economy getEconomy() {
    RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager().getRegistration(Economy.class);
    return service == null ? null : service.getProvider();
  }

  protected static @Nullable Permission getPermission() {
    RegisteredServiceProvider<Permission> service = Bukkit.getServicesManager().getRegistration(Permission.class);
    return service == null ? null : service.getProvider();
  }

  protected static @Nullable Chat getChat() {
    RegisteredServiceProvider<Chat> service = Bukkit.getServicesManager().getRegistration(Chat.class);
    return service == null ? null : service.getProvider();
  }

  public static boolean isAvailable(@NotNull Component component) {
    Validate.Arg.notNull(component, "component");
    return switch (component) {
      case ECO -> getEconomy() != null;
      case CHAT -> getChat() != null;
      case PERM -> getPermission() != null;
    };
  }

  public static double getMoney(@NotNull OfflinePlayer player) {
    return getEconomy() == null ? 0.0 : getEconomy().getBalance(player);
  }

  public static void deposit(@NotNull OfflinePlayer player, double amount) {
    getEconomy().depositPlayer(player, amount);
  }

  public static boolean hasMoney(@NotNull OfflinePlayer player, double amount) {
    return getEconomy() != null && getEconomy().has(player, amount);
  }

  public static void addPermission(@NotNull OfflinePlayer player, @NotNull String permission) {
    getPermission().playerAddTransient(player, permission);
  }

  public static void removePermission(@NotNull OfflinePlayer player, @NotNull String permission) {
    getPermission().playerRemoveTransient(player, permission);
  }

  public static @NotNull String getDisplayName(@NotNull Player player) {
    Chat chat = getChat();
    if (chat == null) {
      return player.getDisplayName();
    } else {
      String prefix = chat.getPlayerPrefix(player);
      String suffix = chat.getPlayerSuffix(player);
      return prefix + player.getName() + suffix;
    }
  }

  /**
   * Withdraws an amount from a player. DO NOT use negative amounts.
   *
   * @param player the player
   * @param amount the amount
   */
  public static void withdraw(OfflinePlayer player, @Range(from = 0, to = Long.MAX_VALUE) double amount) {
    getEconomy().withdrawPlayer(player, amount);
  }

  public static @NotNull VaultBalance getBalance(OfflinePlayer player) {
    return new VaultBalance(player);
  }

  public static @NotNull String getGroup(@NotNull Player player) {
    Validate.Arg.notNull(player, "player");
    if (getPermission() == null) {
      return "default";
    } else {
      return getPermission().hasGroupSupport() ? getPermission().getPrimaryGroup(player) : "default";
    }
  }

  public enum Component {
    /**
     * Economy.
     */
    ECO,
    /**
     * Chat.
     */
    CHAT,
    /**
     * Permission.
     */
    PERM
  }

}
