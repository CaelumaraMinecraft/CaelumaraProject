package top.auspice.bukkit.server.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.text.compiler.TextObject;
import top.auspice.bukkit.server.core.BukkitServer;
import net.aurika.text.placeholders.context.TextPlaceholderProvider;
import top.auspice.server.core.Server;
import top.auspice.server.entity.Entity;
import top.auspice.server.location.Location;

import java.util.Objects;
import java.util.UUID;

public class BukkitEntity implements Entity {
    protected final BukkitServer server;
    protected final org.bukkit.entity.Entity entity;
    protected final UUID uniqueId;

    public BukkitEntity(BukkitServer server, org.bukkit.entity.Entity entity) {
        Objects.requireNonNull(server);
        Objects.requireNonNull(entity);
        this.server = server;
        this.entity = entity;
        this.uniqueId = entity.getUniqueId();
    }

    @Override
    public @NotNull Location getLocationCopy() {
        org.bukkit.Location loc = this.entity.getLocation();
        return new Location(
                this.server.getWorldRegistry().getWorld(loc.getWorld()),
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch()
        );
    }

    @Override
    public Location joinLocation(@Nullable Location location) {
        if (location == null) return null;
        org.bukkit.Location loc = this.entity.getLocation();
        location.setWorld(this.server.getWorldRegistry().getWorld(loc.getWorld()));
        location.setX(loc.getX());
        location.setY(loc.getY());
        location.setZ(loc.getZ());
        location.setYaw(loc.getYaw());
        location.setPitch(loc.getPitch());
        return location;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public void sendMessage(@NotNull TextObject message) {
        this.entity.sendMessage(message.buildPlain(TextPlaceholderProvider.DEFAULT));  // Craftbukkit server only supported plain message
    }

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @Override
    public @NotNull String getName() {
        return "";
    }
}
