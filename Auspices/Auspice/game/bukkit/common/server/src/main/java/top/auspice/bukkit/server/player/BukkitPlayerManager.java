package top.auspice.bukkit.server.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.aurika.annotations.data.SyncedData;
import top.auspice.bukkit.server.core.BukkitServer;
import top.auspice.platform.bukkit.entity.BukkitPlayer;
import top.auspice.server.player.PlayerManager;
import top.auspice.utils.nonnull.NonNullMap;
import top.auspice.utils.typemapping.TypeMappingCollection;

import java.util.*;

public class BukkitPlayerManager implements PlayerManager {
    private final BukkitServer server;
    private final TypeMappingCollection<? extends Player, BukkitPlayer> onlinePlayers = new TypeMappingCollection<>((Collection<Player>) Bukkit.getOnlinePlayers(), player -> BukkitPlayerManager.this.getPlayer(player), BukkitPlayer::getRealValue);
    protected final Map<Player, BukkitPlayer> playerMapping = new NonNullMap<>();

    public BukkitPlayerManager(BukkitServer server) {
        Objects.requireNonNull(server);
        this.server = server;
    }

    public BukkitPlayer getPlayer(@NotNull UUID id) {
        return this.getPlayer(Bukkit.getPlayer(id));
    }

    public BukkitPlayer getPlayer(@NotNull String name) {
        return this.getPlayer(Bukkit.getPlayer(name));
    }

    public BukkitPlayer getPlayer(org.bukkit.entity.Player player) {
        if (player == null) return null;
        BukkitPlayer p1 = this.playerMapping.get(player);
        if (p1 != null) return p1;
        BukkitPlayer p2 = BukkitPlayer.of(this.server, player);
        this.playerMapping.put(player, p2);
        return p2;
    }

    @Override
    public @NotNull @SyncedData Collection<? extends BukkitPlayer> getOnlinePlayers() {
        return this.onlinePlayers;
    }

    @Override
    public @NotNull BukkitOfflinePlayer getOfflinePlayer(@NotNull UUID id) {
        return BukkitOfflinePlayer.of(Bukkit.getOfflinePlayer(id));
    }

    @Override
    public @NotNull Set<BukkitOfflinePlayer> getOperators() {
        final Set<BukkitOfflinePlayer> result = new HashSet<>();
        for (final org.bukkit.OfflinePlayer player : Bukkit.getOperators()) {
            result.add(BukkitOfflinePlayer.of(player));
        }
        return result;
    }
}
