package top.auspice.craftbukkit.services.vault;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import top.auspice.bukkit.services.BukkitService;
import top.auspice.bukkit.services.ServiceEconomy;
import top.auspice.craftbukkit.services.vault.constants.VaultBalance;

public final class ServiceVault implements BukkitService, ServiceEconomy {
    public ServiceVault() {
    }

    private static Economy getEconomy() {
        RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager().getRegistration(Economy.class);
        return service == null ? null : service.getProvider();
    }

    private static Permission getPermission() {
        RegisteredServiceProvider<Permission> service = Bukkit.getServicesManager().getRegistration(Permission.class);
        return service == null ? null : service.getProvider();
    }

    private static Chat getChat() {
        RegisteredServiceProvider<Chat> service = Bukkit.getServicesManager().getRegistration(Chat.class);
        return service == null ? null : service.getProvider();
    }

    public static boolean isAvailable(Component component) {
        return switch (component) {
            case ECO -> getEconomy() != null;
            case CHAT -> getChat() != null;
            case PERM -> getPermission() != null;
        };
    }

    public static double getMoney(OfflinePlayer player) {
        return getEconomy() == null ? 0.0 : getEconomy().getBalance(player);
    }

    public static void deposit(OfflinePlayer player, double amount) {
        getEconomy().depositPlayer(player, amount);
    }

    public static boolean hasMoney(OfflinePlayer player, double amount) {
        return getEconomy() != null && getEconomy().has(player, amount);
    }

    public static void addPermission(OfflinePlayer player, String permission) {
        getPermission().playerAddTransient(player, permission);
    }

    public static void removePermission(OfflinePlayer player, String permission) {
        getPermission().playerRemoveTransient(player, permission);
    }

    public static String getDisplayName(Player player) {
        Chat chat = getChat();
        if (chat == null) {
            return player.getDisplayName();
        } else {
            String prefix = chat.getPlayerPrefix(player);
            String suffix = chat.getPlayerSuffix(player);
            return prefix + player.getName() + suffix;
        }
    }

    public static void withdraw(OfflinePlayer player, double amount) {
        getEconomy().withdrawPlayer(player, amount);
    }

    public static VaultBalance getBalance(OfflinePlayer player) {
        return new VaultBalance(player);
    }

    public static String getGroup(Player player) {
        if (getPermission() == null) {
            return "default";
        } else {
            return getPermission().hasGroupSupport() ? getPermission().getPrimaryGroup(player) : "default";
        }
    }

    public enum Component {
        ECO,
        CHAT,
        PERM
    }
}
