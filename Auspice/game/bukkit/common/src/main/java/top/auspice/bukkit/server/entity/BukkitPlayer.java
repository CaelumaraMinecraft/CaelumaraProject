package top.auspice.bukkit.server.entity;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.bukkit.server.core.BukkitServer;

import top.auspice.bukkit.server.location.BukkitWorld;
import top.auspice.server.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class BukkitPlayer extends BukkitEntity implements Player {
    private final org.bukkit.entity.Player player;
    private final String name;

    @Contract("null -> null; !null -> new")
    public static BukkitPlayer of(org.bukkit.entity.Player player) {
        return of(BukkitServer.get(), player);
    }

    @Contract("null, _ -> fail; _, null -> null; _, !null -> new")
    public static BukkitPlayer of(BukkitServer server, org.bukkit.entity.Player player) {
        Objects.requireNonNull(server);
        if (player == null) return null;
        return new BukkitPlayer(server, player);
    }

    public BukkitPlayer(@NotNull BukkitServer server, org.bukkit.entity.Player player) {
        super(server, player);  //TODO server
        Objects.requireNonNull(player);
        this.player = player;
        this.name = player.getName();
    }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Cannot use method on fake player");
    }

    public @Nullable org.bukkit.entity.Player getRealValue() {
        return this.player;
    }

    public @NotNull BukkitWorld getWorld() {
        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(this.uniqueId);
        if (bukkitPlayer == null) {
            throw new IllegalArgumentException("Fake player " + this.uniqueId + " is not online");
        }
        return BukkitWorld.of(bukkitPlayer.getWorld());
    }

    public @NotNull UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public @Nullable org.bukkit.entity.Player getRealPlayer() {
        return this.player;
    }

    public @NotNull String getName() {
        return this.name;
    }
}
