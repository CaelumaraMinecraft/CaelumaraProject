package net.aurika.auspice.bukkit.platform.entity;

import net.aurika.auspice.bukkit.server.core.BukkitServer;
import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.entity.Entity;
import net.aurika.auspice.platform.location.AbstractLocationMutable;
import net.aurika.auspice.text.compiler.TextObject;
import net.aurika.text.placeholders.context.TextPlaceholderProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
  public @NotNull AbstractLocationMutable getLocationCopy() {
    org.bukkit.Location loc = this.entity.getLocation();
    return new AbstractLocationMutable(
        this.server.getWorldRegistry().getWorld(loc.getWorld()),
        loc.getX(),
        loc.getY(),
        loc.getZ(),
        loc.getYaw(),
        loc.getPitch()
    );
  }

  @Override
  public AbstractLocationMutable joinLocation(@Nullable AbstractLocationMutable location) {
    if (location == null) return null;
    org.bukkit.Location loc = this.entity.getLocation();
    location.world(this.server.getWorldRegistry().getWorld(loc.getWorld()));
    location.floatX(loc.getX());
    location.floatY(loc.getY());
    location.floatZ(loc.getZ());
    location.yaw(loc.getYaw());
    location.pitch(loc.getPitch());
    return location;
  }

  @Override
  public @NotNull UUID getUniqueId() {
    return this.uniqueId;
  }

  @Override
  public void sendMessage(@NotNull TextObject message) {
    this.entity.sendMessage(
        message.buildPlain(TextPlaceholderProvider.DEFAULT));  // Craftbukkit server only supported plain message
  }

  @Override
  public @NotNull Platform getServer() {
    return this.server;
  }

  @Override
  public @NotNull String getName() {
    return "";
  }

}
