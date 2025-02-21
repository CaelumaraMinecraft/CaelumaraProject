package net.aurika.auspice.service.api;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BukkitServicePet extends BukkitService {
    @Nullable PetInfo petInfo(@NotNull Entity entity);
}
