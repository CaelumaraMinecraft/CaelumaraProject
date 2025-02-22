package net.aurika.auspice.game.bukkit.server.player;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.server.player.OfflinePlayer;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class BukkitOfflinePlayer implements OfflinePlayer, org.bukkit.OfflinePlayer {
    protected final UUID id;
    protected final String name;

    @Contract("_ -> new")
    public static BukkitOfflinePlayer of(final org.bukkit.OfflinePlayer player) {
        return new BukkitOfflinePlayer(player.getUniqueId(), player.getName());
    }

    public BukkitOfflinePlayer(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public org.bukkit.OfflinePlayer getRealOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.id);
    }

    @Override
    public boolean isOnline() {
        return Bukkit.getPlayer(id) != null;
    }

    @Override
    public @Nullable String getName() {
        return this.name;
    }

    @Override
    public @NotNull PlayerProfile getPlayerProfile() {
        throw unsupported();
    }

    @Override
    public boolean isBanned() {
        throw unsupported();
    }

    @Override
    public @Nullable BanEntry<PlayerProfile> ban(@Nullable String s, @Nullable Date date, @Nullable String s1) {
        throw unsupported();
    }

    @Override
    public @Nullable BanEntry<PlayerProfile> ban(@Nullable String s, @Nullable Instant instant, @Nullable String s1) {
        throw unsupported();
    }

    @Override
    public @Nullable BanEntry<PlayerProfile> ban(@Nullable String s, @Nullable Duration duration, @Nullable String s1) {
        throw unsupported();
    }

    @Override
    public boolean isWhitelisted() {
        throw unsupported();
    }

    @Override
    public void setWhitelisted(boolean b) {
        throw unsupported();
    }

    @Override
    public @Nullable Player getPlayer() {
        return Bukkit.getPlayer(id);
    }

    @Override
    public long getFirstPlayed() {
        throw unsupported();
    }

    @Override
    public long getLastPlayed() {
        throw unsupported();
    }

    @Override
    public boolean hasPlayedBefore() {
        throw unsupported();
    }

    @Override
    public @Nullable Location getBedSpawnLocation() {
        throw unsupported();
    }

    @Override
    public @Nullable Location getRespawnLocation() {
        throw unsupported();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) throws IllegalArgumentException {
        throw unsupported();
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) {
        throw unsupported();
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) {
        throw unsupported();
    }

    @Override
    public @Nullable Location getLastDeathLocation() {
        throw unsupported();
    }

    @Override
    public @Nullable Location getLocation() {
        throw unsupported();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        throw unsupported();
    }

    @Override
    public boolean isOp() {
        throw unsupported();
    }

    @Override
    public void setOp(boolean b) {
        throw unsupported();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.id;
    }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Cannot use method on fake offline player");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BukkitOfflinePlayer that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
