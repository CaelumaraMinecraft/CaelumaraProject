package net.aurika.auspice.platform.bukkit.api.entity;

import net.aurika.auspice.platform.entity.abstraction.Entity;
import org.jetbrains.annotations.NotNull;

public interface BukkitEntity extends Entity {

  @NotNull org.bukkit.entity.Entity bukkitObject();

  interface Adapter<AE extends BukkitEntity, PE extends org.bukkit.entity.Entity> extends Entity.Adapter<AE, PE> { }

}
